package org.myaldoc.core.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.myaldoc.core.exceptions.ExceptionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Project MYALDOC
 * @Author Henri Joel SEDJAME
 * @Date 15/12/2018
 * @Class purposes : .......
 */
@Component
@Aspect
@Slf4j
public class ExceptionBuilderAspects {
    @Autowired
    ExceptionBuilder exceptionBuilder;

    @Before(value = "@annotation(org.myaldoc.core.aspects.annotations.ExceptionBuilderClearBefore)")
    public void clearBefore() {
        exceptionBuilder.clear();
    }

    @After("@annotation(org.myaldoc.core.aspects.annotations.ExceptionBuilderClearAfter)")
    public void clearAfter() {
        exceptionBuilder.clear();
    }
}
