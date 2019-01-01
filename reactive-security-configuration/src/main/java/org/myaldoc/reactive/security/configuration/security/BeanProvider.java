package org.myaldoc.reactive.security.configuration.security;

import org.myaldoc.reactive.security.core.jwt.JwtProperties;
import org.myaldoc.reactive.security.core.jwt.JwtUtils;
import org.myaldoc.reactive.security.core.repositories.UserRepository;
import org.myaldoc.reactive.security.core.services.impl.MyaldocReactiveUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Project REACTIVE_SECURITY_JWT
 * @Author Henri Joel SEDJAME
 * @Date 31/12/2018
 * @Class purposes : .......
 */
@Configuration
public class BeanProvider {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProperties jwtProperties;

    @Bean
    public MyaldocReactiveUserDetailsService userService(){
        return new MyaldocReactiveUserDetailsService(userRepository);
    }

    @Bean
    public JwtUtils jwtUtils(){
        return new JwtUtils(jwtProperties);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
