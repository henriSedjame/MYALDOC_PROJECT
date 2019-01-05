package org.myaldoc.reactive.security.resource.server.configuration.security;

import io.jsonwebtoken.JwtException;
import org.myaldoc.reactive.security.core.jwt.JwtUtils;
import org.myaldoc.reactive.security.core.models.User;
import org.myaldoc.reactive.security.core.services.impl.MyaldocReactiveUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Project REACTIVE_SECURITY_JWT
 * @Author Henri Joel SEDJAME
 * @Date 31/12/2018
 * @Class purposes : .......
 */
@Component
public class ReactiveResourceServerAuthenticationManager implements ReactiveAuthenticationManager {

    @Autowired
    WebClient webClient;
    @Autowired
    private JwtUtils jwtUtils;

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

        return webClient.get()
                .uri("http://localhost:9000/user/retrieve/" + username)
                .retrieve()
                .bodyToMono(User.class)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new BadCredentialsException("Invalid Credentials"))))
                .flatMap(Mono::just)
                .map(u -> new UsernamePasswordAuthenticationToken(u, u.getPassword(), u.getRoles().stream().map(r -> new SimpleGrantedAuthority(r.getRoleName())).collect(Collectors.toList())));

    }

}
