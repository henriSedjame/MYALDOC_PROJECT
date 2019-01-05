package org.myaldoc.gestion.documents.service.configuration;

import org.myaldoc.gestion.documents.service.exceptions.GestionDocumentException;
import org.myaldoc.gestion.documents.service.exceptions.GestionDocumentExceptionBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @Project MYALDOC
 * @Author Henri Joel SEDJAME
 * @Date 24/12/2018
 * @Class purposes : .......
 */
@Configuration
public class BeanProvider {

    @Bean
    GestionDocumentExceptionBuilder exceptionBuilder() {
        return new GestionDocumentExceptionBuilder(GestionDocumentException.class);
    }

    @Bean
    WebClient webClient(){
        return WebClient.create();
    }
}
