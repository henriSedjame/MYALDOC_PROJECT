package org.myaldoc.gestion.documents.service.exceptions;

import org.myaldoc.core.exceptions.ExceptionBuilder;

/**
 * @Project MYALDOC
 * @Author Henri Joel SEDJAME
 * @Date 24/12/2018
 * @Class purposes : .......
 */
public class GestionDocumentExceptionBuilder extends ExceptionBuilder<GestionDocumentException> {

    public GestionDocumentExceptionBuilder(Class<GestionDocumentException> exceptionClass) {
        super(exceptionClass);
    }
}
