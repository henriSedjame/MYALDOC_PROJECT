package org.myaldoc.reactive.security.resource.server.configuration.security;

import io.jsonwebtoken.JwtException;
import org.myaldoc.reactive.security.core.jwt.JwtUtils;
import org.myaldoc.reactive.security.core.models.User;
import org.myaldoc.reactive.security.core.services.impl.MyaldocReactiveUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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

    //********************************************************************************************************************
    // CONSTANTES
    //********************************************************************************************************************

    private static final String MYALDOC_AUTH_USER_RETRIEVE_URI = "myaldoc.auth.userRetrieveUri";
    private static final String AUTH_CONFIG_MISSING_ERROR_MSG = "UserRetrieve uri is missing, " +
                                                                "please consider to configure " +
                                                                "\"myaldoc.auth.userRetrieveUri = \" " +
                                                                "in your properties file";

    //********************************************************************************************************************
    // ATTRIBUTS
    //********************************************************************************************************************

    @Autowired
    WebClient webClient;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    Environment environment;

    //********************************************************************************************************************
    // METHODES
    //********************************************************************************************************************

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {

        String userRetrieveUri = environment.getProperty(MYALDOC_AUTH_USER_RETRIEVE_URI);

        if (Objects.isNull(userRetrieveUri))
            return Mono.error(new Exception(AUTH_CONFIG_MISSING_ERROR_MSG));
        else if(! userRetrieveUri.endsWith("/"))
            userRetrieveUri = userRetrieveUri + "/";

        final String token = authentication.getCredentials().toString();

        String username;
        try {
            username = jwtUtils.getUsernameFromToken(token);
        }catch (Exception e){
            username = null;
        }
        if (Objects.isNull(username)) return Mono.error(new JwtException("Username not found in token"));

        return webClient.get()
                .uri(userRetrieveUri + username)
                .retrieve()
                .bodyToMono(User.class)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new BadCredentialsException("Invalid Credentials"))))
                .flatMap(Mono::just)
                .map(u -> new UsernamePasswordAuthenticationToken(u, u.getPassword(), u.getRoles().stream().map(r -> new SimpleGrantedAuthority(r.getRoleName())).collect(Collectors.toList())));

    }

}
