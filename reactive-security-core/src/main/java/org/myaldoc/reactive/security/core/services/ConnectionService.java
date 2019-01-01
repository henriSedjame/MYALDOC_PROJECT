package org.myaldoc.reactive.security.core.services;

import org.myaldoc.reactive.security.core.models.Role;
import org.myaldoc.reactive.security.core.models.User;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface ConnectionService {

  Mono<User> saveUser(@Valid User user);

  Mono<Role> saveRole(@Valid Role role);

  Mono<User> updateUser(@Valid User user);

  Mono<User> activateUser(@NotNull String userId);

  Mono<User> addRoleToUser(@NotNull String username, @NotNull String rolename);

  Mono<User> retrieveUser(@NotNull String username);

  Mono<Void> deleteUser(@NotNull String userId);

  Mono<User> changePassword(User user);

}
