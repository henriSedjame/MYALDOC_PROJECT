package org.myaldoc.reactive.security.core.configuration.beans;

import org.myaldoc.reactive.security.core.exceptions.ConnectionException;
import org.myaldoc.reactive.security.core.exceptions.ConnectionExceptionBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Project MYALDOC
 * @Author Henri Joel SEDJAME
 * @Date 15/12/2018
 * @Class purposes : .......
 */
@Configuration
public class BeanProvider {

    @Bean
    public ConnectionExceptionBuilder connectionExceptionBuilder() {
        return new ConnectionExceptionBuilder(ConnectionException.class);
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
