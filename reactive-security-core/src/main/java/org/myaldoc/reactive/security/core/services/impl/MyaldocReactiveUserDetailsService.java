package org.myaldoc.reactive.security.core.services.impl;


import org.myaldoc.reactive.security.core.models.User;
import org.myaldoc.reactive.security.core.repositories.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsPasswordService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

/**
 * @Project REACTIVE_SECURITY_JWT
 * @Author Henri Joel SEDJAME
 * @Date 31/12/2018
 * @Class purposes : .......
 */
@Service
public class MyaldocReactiveUserDetailsService implements ReactiveUserDetailsService, ReactiveUserDetailsPasswordService {

    //********************************************************************************************************************
    // ATTRIBUTS
    //********************************************************************************************************************

    private final UserRepository userRepository;

    //********************************************************************************************************************
    // CONSTRUCTEUR
    //********************************************************************************************************************

    public MyaldocReactiveUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //********************************************************************************************************************
    // METHODES
    //********************************************************************************************************************

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return this.userRepository.findByUsername(username)
                .map(this::convert);
    }

    @Override
    public Mono<UserDetails> updatePassword(UserDetails user, String newPassword) {
        return this.userRepository
                .findByUsername(user.getUsername())
                .map(u -> {
                    u.setPassword(newPassword);
                    return u;
                })
                .flatMap(userRepository::save)
                .map(this::convert);
    }

    //********************************************************************************************************************
    // METHODES PRIVATE
    //********************************************************************************************************************

    private UserDetails convert(User user){
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(),
                user.getRoles().stream().map(r -> new SimpleGrantedAuthority(r.getRoleName())).collect(Collectors.toList())
        );
    }

}
