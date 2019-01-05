package org.myaldoc.reactive.security.auth.server.configuration.annotations;

import org.myaldoc.reactive.security.auth.server.configuration.config.CorsFilter;
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
@Import({CorsFilter.class})
public @interface EnableCorsFilter {
}
