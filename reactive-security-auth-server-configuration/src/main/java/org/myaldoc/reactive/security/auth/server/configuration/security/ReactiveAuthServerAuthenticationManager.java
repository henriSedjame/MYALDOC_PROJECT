package org.myaldoc.reactive.security.auth.server.configuration.security;

import io.jsonwebtoken.JwtException;
import org.myaldoc.reactive.security.core.jwt.JwtUtils;
import org.myaldoc.reactive.security.core.services.impl.MyaldocReactiveUserDetailsService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * @Project REACTIVE_SECURITY_JWT
 * @Author Henri Joel SEDJAME
 * @Date 31/12/2018
 * @Class purposes : .......
 */
@Component
public class ReactiveAuthServerAuthenticationManager implements ReactiveAuthenticationManager {

    private MyaldocReactiveUserDetailsService userService;
    private JwtUtils jwtUtils;


    public ReactiveAuthServerAuthenticationManager(MyaldocReactiveUserDetailsService userDetailsService, JwtUtils jwtUtils) {
        this.userService = userDetailsService;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        final String token = authentication.getCredentials().toString();

        String username;
        try {
            username = jwtUtils.getUsernameFromToken(token);
        }catch (Exception e){
            username = null;
        }
        if (Objects.isNull(username)) return Mono.error(new JwtException("Username not found in token"));

        return this.userService.findByUsername(username)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new BadCredentialsException("Invalid Credentials"))))
                .flatMap(Mono::just)
                .map(u -> new UsernamePasswordAuthenticationToken(u, u.getPassword(), u.getAuthorities()));
    }

}
