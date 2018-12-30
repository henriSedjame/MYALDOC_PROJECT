package org.myaldoc.security.core.configuration.annotations;

import org.myaldoc.security.core.configuration.MethodSecurityConfiguration;
import org.myaldoc.security.core.configuration.ResourceServerConfiguration;
import org.myaldoc.security.core.configuration.WebMvcConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Project MYALDOC_PORJECT
 * @Author Henri Joel SEDJAME
 * @Date 30/12/2018
 *
 * Cette annotation permet de configurer les microservices comme resource server Oauth2
 *
 *
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({WebMvcConfiguration.class, ResourceServerConfiguration.class, MethodSecurityConfiguration.class})
public @interface MyaldocOauth2ResourceServer {
}
