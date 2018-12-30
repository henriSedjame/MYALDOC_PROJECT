package org.myaldoc.core.aspects.annotations;

import org.myaldoc.core.aspects.LoggingAspects;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Project MYALDOC_PORJECT
 * @Author Henri Joel SEDJAME
 * @Date 30/12/2018
 * @Class purposes : .......
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({LoggingAspects.class})
public @interface EnableLoggingAspect {
}
