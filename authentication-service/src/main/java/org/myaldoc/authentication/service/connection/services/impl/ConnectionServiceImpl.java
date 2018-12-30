package org.myaldoc.authentication.service.connection.services.impl;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.myaldoc.authentication.service.connection.exceptions.ConnectionExceptionBuilder;
import org.myaldoc.authentication.service.connection.exceptions.ConnectionExceptionMessages;
import org.myaldoc.authentication.service.connection.messaging.EmailSource;
import org.myaldoc.authentication.service.connection.models.Role;
import org.myaldoc.authentication.service.connection.models.User;
import org.myaldoc.authentication.service.connection.repositories.RoleRepository;
import org.myaldoc.authentication.service.connection.repositories.UserRepository;
import org.myaldoc.authentication.service.connection.services.ConnectionService;
import org.myaldoc.authentication.service.connection.services.NotificationSender;
import org.myaldoc.core.aspects.annotations.ExceptionBuilderClearBefore;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.text.MessageFormat;

@Service
@EnableBinding(EmailSource.class)
@Getter
@Slf4j
public class ConnectionServiceImpl implements ConnectionService {

  //********************************************************************************************************************
  // ATTRIBUTS
  //********************************************************************************************************************

  private UserRepository userRepository;
  private RoleRepository roleRepository;
  private BCryptPasswordEncoder passwordEncoder;
  private ConnectionExceptionMessages exceptionMessages;
  private ConnectionExceptionBuilder exceptionBuilder;
  private NotificationSender notificationSender;

  //********************************************************************************************************************
  // CONSTRUCTEUR
  //********************************************************************************************************************
  public ConnectionServiceImpl(UserRepository userRepository,
                               RoleRepository roleRepository,
                               BCryptPasswordEncoder passwordEncoder,
                               NotificationSender notificationSender,
                               ConnectionExceptionBuilder exceptionBuilder,
                               ConnectionExceptionMessages exceptionMessages) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.passwordEncoder = passwordEncoder;
    this.exceptionMessages = exceptionMessages;
    this.notificationSender = notificationSender;
    this.exceptionBuilder = exceptionBuilder;
  }

  //********************************************************************************************************************
  // METHODES
  //********************************************************************************************************************

  /**
   * SAUVEGARDE D'UN USER
   *
   * @param user
   * @return
   */
  @Override
  @ExceptionBuilderClearBefore
  public Mono<User> saveUser(User user) {
    return this.userRepository
            .existsByUsername(user.getUsername())
            .flatMap(exist -> {
              if (exist)
                return Mono.error(this.exceptionBuilder.buildException(MessageFormat.format(this.exceptionMessages.getUserAlreadyExist(), user.getUsername()), null));

              /*cryptage du mot de passe*/
              user.setPassword(this.passwordEncoder.encode(user.getPassword()));

              /*Initialisation de la propriété enabled à false*/
              user.setEnabled(false);
              try {
                return this.userRepository.save(user)
                        .flatMap(us -> this.addRoleToUser(us.getUsername(), Role.USER))
                        .doOnSuccess(u -> this.notificationSender.notifyAccountCreation(u));
              } catch (Exception e) {
                return Mono.error(this.exceptionBuilder.buildException(MessageFormat.format(this.exceptionMessages.getUserSavingError(), user.getUsername()), e));
              }
            });
  }

  /**
   * SAUVEGARDE D'UN ROLE
   * @param role
   * @return
   */
  @Override
  public Mono<Role> saveRole(Role role) {
    return this.roleRepository.insert(role);
  }

  /**
   * MISE A JOUR D'UN USER
   * @param user
   * @return
   */
  @Override
  @ExceptionBuilderClearBefore
  public Mono<User> updateUser(User user) {
    return Mono.defer(() -> {
      try {
        return this.userRepository.save(user);
      } catch (Exception e) {
        return Mono.error(this.exceptionBuilder.buildException(MessageFormat.format(this.exceptionMessages.getUserSavingError(), user.getUsername()), e));
      }
    });
  }

  /**
   * ACTIVATION D'UN USER
   *
   * @param userId
   * @return
   */
  @Override
  public Mono<User> activateUser(String userId) {
    return this.userRepository
            .findById(userId)
            .flatMap(user -> {
              user.setEnabled(true);
              return this.userRepository.save(user).doOnSuccess(u -> this.notificationSender.notifyAccountActivation(u));
            });
  }

  /**
   * AJOUT D'UN ROLE A UN USER
   * @param username
   * @param rolename
   * @return
   */
  @Override
  public Mono<User> addRoleToUser(String username, String rolename) {
    return Mono.zip(this.userRepository.findByUsername(username), this.roleRepository.findByRoleName(rolename))
            .flatMap(tuple -> {
              User user = tuple.getT1();
              Role role = tuple.getT2();
              user.getRoles().add(role);
              return this.updateUser(user);
            });
  }

  /**
   * RECHERCHER UN USER
   * @param username
   * @return
   */
  @Override
  @ExceptionBuilderClearBefore
  public Mono<User> retrieveUser(String username) {
    return Mono.defer(() -> {
      try {
        return this.userRepository.findByUsername(username);
      } catch (Exception e) {
        return Mono.error(this.exceptionBuilder.buildException(MessageFormat.format(this.exceptionMessages.getUserRetrievingError(), username), e));
      }
    });

  }

  @Override
  @ExceptionBuilderClearBefore
  public Mono<Void> deleteUser(String userId) {
    return Mono.defer(() -> {
      try {
        return this.userRepository
                .findById(userId)
                .flatMap(user -> this.userRepository.deleteById(userId)
                        .then()
                        .doOnSuccess(x -> this.notificationSender.notifyAccountDeletion(user))
                );
      } catch (Exception e) {
        return Mono.error(this.exceptionBuilder.buildException(MessageFormat.format(this.exceptionMessages.getAccountDeletionError(), userId), e));
      }
    });
  }

}
