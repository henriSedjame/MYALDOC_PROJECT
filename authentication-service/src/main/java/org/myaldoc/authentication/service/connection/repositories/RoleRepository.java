package org.myaldoc.authentication.service.connection.repositories;

import org.myaldoc.authentication.service.connection.models.Role;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface RoleRepository extends ReactiveMongoRepository<Role, String> {

  Mono<Role> findByRoleName(String rolename);
}
