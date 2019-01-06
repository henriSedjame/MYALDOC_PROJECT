package org.myaldoc.gestion.documents.service.resources;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.myaldoc.gestion.documents.service.resources.ApiRoutesUtils.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.*;

/**
 * @Project MYALDOC_PORJECT
 * @Author Henri Joel SEDJAME
 * @Date 05/01/2019
 * @Class purposes : .......
 */
@Configuration
public class ApiRouteDefinitions {

    @Bean
    RouterFunction<?> routes(RouterHandlers handlers){
        return route(POST(FICHIER_SAVE).and(accept(MediaType.ALL)), handlers::handleSaveFile)
                .andRoute(GET(FICHIER_RETRIEVE), handlers::handleRetrieveFile)
                .andRoute(DELETE(FICHIER_DELETE), handlers::handleDeleteFile);
    }
}
