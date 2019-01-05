package org.myaldoc.reactive.security.auth.server.configuration.annotations;

import org.myaldoc.reactive.security.auth.server.configuration.config.CorsFilter;
import org.myaldoc.reactive.security.auth.server.configuration.config.WebFluxSecurityConfig;
import org.myaldoc.reactive.security.auth.server.configuration.security.ReactiveAuthServerAuthenticationManager;
import org.myaldoc.reactive.security.auth.server.configuration.security.SecurityContextRepository;
import org.myaldoc.reactive.security.core.configuration.beans.BeanProvider;
import org.myaldoc.reactive.security.core.exceptions.ConnectionExceptionMessages;
import org.myaldoc.reactive.security.core.jwt.JwtProperties;
import org.myaldoc.reactive.security.core.jwt.JwtUtils;
import org.myaldoc.reactive.security.core.services.impl.ConnectionServiceImpl;
import org.myaldoc.reactive.security.core.services.impl.MyaldocReactiveUserDetailsService;
import org.myaldoc.reactive.security.core.services.impl.NotificationSenderImpl;
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
@Import({ConnectionServiceImpl.class,
        NotificationSenderImpl.class,
        BeanProvider.class,
        ConnectionExceptionMessages.class,
        MyaldocReactiveUserDetailsService.class,
        SecurityContextRepository.class,
        ReactiveAuthServerAuthenticationManager.class,
        WebFluxSecurityConfig.class,
        CorsFilter.class,
        JwtUtils.class,
        JwtProperties.class})
public @interface EnableReactiveAuthServerSecurity {
}
