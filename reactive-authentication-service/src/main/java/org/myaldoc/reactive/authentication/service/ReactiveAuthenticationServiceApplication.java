package org.myaldoc.reactive.authentication.service;

import org.myaldoc.reactive.security.configuration.annotations.EnableReactiveSecurity;
import org.myaldoc.reactive.security.core.models.Role;
import org.myaldoc.reactive.security.core.repositories.RoleRepository;
import org.myaldoc.reactive.security.core.repositories.UserRepository;
import org.myaldoc.reactive.security.core.services.ConnectionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.util.stream.Stream;

@SpringBootApplication
@EnableReactiveSecurity
public class ReactiveAuthenticationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveAuthenticationServiceApplication.class, args);
    }

    @Bean
    @Profile("dev")
    CommandLineRunner employees(UserRepository userRepository, RoleRepository roleRepository, ConnectionService service) {
        return args -> {
            roleRepository
                    .deleteAll()
                    .subscribe(null, null, () -> {
                        Stream.of(
                                new Role(null, Role.ADMIN),
                                new Role(null, Role.USER)
                        ).forEach(role -> {
                            service
                                    .saveRole(role)
                                    .subscribe(System.out::println);
                        });
                    });

        };

    }
}

