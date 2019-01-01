package org.myaldoc.reactive.security.core.repositories;

import org.myaldoc.reactive.security.core.models.Role;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface RoleRepository extends ReactiveMongoRepository<Role, String> {

  Mono<Role> findByRoleName(String rolename);
}
