package org.myaldoc.reactive.security.core.services.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.myaldoc.reactive.security.core.comparators.Comparators;
import org.myaldoc.reactive.security.core.exceptions.ConnectionExceptionBuilder;
import org.myaldoc.reactive.security.core.exceptions.ConnectionExceptionMessages;
import org.myaldoc.reactive.security.core.models.Role;
import org.myaldoc.reactive.security.core.models.User;
import org.myaldoc.reactive.security.core.repositories.RoleRepository;
import org.myaldoc.reactive.security.core.repositories.UserRepository;
import org.myaldoc.reactive.security.core.services.NotificationSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.TreeSet;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @Project MYALDOC_PORJECT
 * @Author Henri Joel SEDJAME
 * @Date 01/01/2019
 * @Class purposes : .......
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConnectionServiceImplTest {
    private static final String USERNAME = "guest";
    private static final String PASSWORD = "Hiphop!87";
    private static final String EMAIL = "guest@gmail.com";
    private static final String EMAIL_2 = "guest2@gmail.com";
    private static final String BAD_EMAIL = "guestgmail.com";
    private static final String USER_ID = "id14587test";
    private static final String EMAIL_INCORRECT = "Le format de l'email est incorrect";

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private ConnectionExceptionMessages exceptionMessages;
    @Mock
    private ConnectionExceptionBuilder exceptionBuilder;
    @Spy
    private NotificationSender notificationSender;

    private ConnectionServiceImpl service;

    private User user;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new ConnectionServiceImpl(userRepository, roleRepository, passwordEncoder, notificationSender, exceptionBuilder, exceptionMessages);

        user = User.builder()
                .id(USER_ID)
                .username(USERNAME)
                .password(PASSWORD)
                .email(EMAIL)
                .roles(new TreeSet<>(Comparators.ROLE_COMPARATOR))
                .build();

        doNothing().when(notificationSender).notifyAccountCreation(any(User.class));
        doNothing().when(notificationSender).notifyAccountDeletion(any(User.class));

        assertAll("service",
                () -> assertNotNull(service),
                () -> assertNotNull(service.getExceptionBuilder()));

    }

    /**
     * Succesful Tests
     **/

    @Test
    public void saveUser() {
        final Mono<User> userMono = service.saveUser(user);

        StepVerifier.create(userMono)
                .assertNext(u -> {
                    assertNotNull(u);
                    assertNotNull(u.getId());
                    assertEquals(USER_ID, u.getId());
                    assertFalse(u.isEnabled());
                    assertTrue(u.getRoles().stream().anyMatch(role -> role.getRoleName().equals(Role.USER)));
                    verify(notificationSender, times(1)).notifyAccountCreation(any(User.class));
                })
                .expectComplete()
                .verify();
    }

    @Test
    public void saveRole() {
    }

    @Test
    public void activateUser() {
        Mono<User> userMono = service.activateUser(USER_ID);

        StepVerifier.create(userMono)
                .assertNext(u -> {
                    assertTrue(u.isEnabled());
                    verify(notificationSender, times(1)).notifyAccountActivation(any(User.class));
                })
                .expectComplete()
                .verify();
    }

    @Test
    public void updateUser() {
        user.setEmail(EMAIL_2);
        final Mono<User> userMono = service.updateUser(user);
        StepVerifier.create(userMono)
                .assertNext(u -> {
                    assertEquals(EMAIL_2, u.getEmail());
                })
                .expectComplete()
                .verify();
    }

    @Test
    public void addRoleToUser() {
        final Mono<User> userMono = service.addRoleToUser(USERNAME, Role.ADMIN);

        StepVerifier.create(userMono)
                .assertNext(u -> {
                    assertTrue(u.getRoles().size() != 0);
                    assertTrue(u.getRoles().stream().anyMatch(r -> r.getRoleName().equals(Role.ADMIN)));
                })
                .expectComplete()
                .verify();
    }

    @Test
    public void retrieveUser() {
        StepVerifier.create(service.retrieveUser(USERNAME))
                .assertNext(u -> {
                    assertAll("user",
                            () -> assertNotNull(u),
                            () -> assertEquals(USERNAME, u.getUsername()),
                            () -> assertEquals(EMAIL_2, u.getEmail()));
                }).expectComplete()
                .verify();
    }

    @Test
    public void deleteUser() {
        service.deleteUser(USER_ID).block();
        verify(notificationSender, times(1)).notifyAccountDeletion(any(User.class));
        final Mono<User> user = userRepository.findById(USER_ID);
        StepVerifier.create(user)
                .expectNext((User) null);
    }

    /**
     * Error tests
     **/

    @Test
    public void shouldFailSave() {
        user.setEmail(BAD_EMAIL);
        final Mono<User> userMono = service.saveUser(user);
        StepVerifier.create(userMono)
                .expectErrorMessage(EMAIL_INCORRECT);
    }

}