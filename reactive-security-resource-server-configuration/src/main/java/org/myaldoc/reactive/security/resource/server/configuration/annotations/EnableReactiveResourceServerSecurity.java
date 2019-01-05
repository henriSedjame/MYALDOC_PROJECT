package org.myaldoc.reactive.security.resource.server.configuration.annotations;

import org.myaldoc.reactive.security.core.configuration.beans.BeanProvider;
import org.myaldoc.reactive.security.core.exceptions.ConnectionExceptionMessages;
import org.myaldoc.reactive.security.core.jwt.JwtProperties;
import org.myaldoc.reactive.security.core.jwt.JwtUtils;
import org.myaldoc.reactive.security.core.services.impl.MyaldocReactiveUserDetailsService;
import org.myaldoc.reactive.security.resource.server.configuration.config.CorsFilter;
import org.myaldoc.reactive.security.resource.server.configuration.config.WebFluxSecurityConfig;
import org.myaldoc.reactive.security.resource.server.configuration.security.ReactiveResourceServerAuthenticationManager;
import org.myaldoc.reactive.security.resource.server.configuration.security.SecurityContextRepository;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Project MYALDOC_PORJECT
 * @Author Henri Joel SEDJAME
 * @Date 01/01/2019
 * @Class purposes : .......
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({BeanProvider.class,
        ConnectionExceptionMessages.class,
        SecurityContextRepository.class,
        ReactiveResourceServerAuthenticationManager.class,
        WebFluxSecurityConfig.class,
        CorsFilter.class,
        JwtUtils.class,
        JwtProperties.class})
public @interface EnableReactiveResourceServerSecurity {
}
