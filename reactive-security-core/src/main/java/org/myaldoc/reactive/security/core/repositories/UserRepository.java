package org.myaldoc.reactive.security.core.repositories;

import org.myaldoc.reactive.security.core.models.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, String> {

  Mono<User> findByUsername(String username);

  Mono<Boolean> existsByUsername(String username);

}
