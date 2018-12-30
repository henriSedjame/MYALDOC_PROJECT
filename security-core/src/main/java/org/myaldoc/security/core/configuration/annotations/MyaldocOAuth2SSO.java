package org.myaldoc.security.core.configuration.annotations;

import org.myaldoc.security.core.configuration.OAuth2SSOConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Project MYALDOC_PORJECT
 * @Author Henri Joel SEDJAME
 * @Date 30/12/2018
 * @Class purposes : .......
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({OAuth2SSOConfiguration.class})
public @interface MyaldocOAuth2SSO {
}
