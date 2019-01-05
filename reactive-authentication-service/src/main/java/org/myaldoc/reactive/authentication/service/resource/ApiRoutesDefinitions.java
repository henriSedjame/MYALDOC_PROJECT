package org.myaldoc.reactive.authentication.service.resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.myaldoc.reactive.authentication.service.resource.ApiRoutesUtils.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * @Project REACTIVE_SECURITY_JWT
 * @Author Henri Joel SEDJAME
 * @Date 31/12/2018
 * @Class purposes : .......
 */
@Configuration
public class ApiRoutesDefinitions {

    @Bean
    RouterFunction<?> routers(RouterHandlers handlers){
        return route(POST(LOGIN).and(accept(MediaType.APPLICATION_JSON)), handlers::handleLogin)
                .andRoute(POST(USER_CREATE).and(accept(MediaType.APPLICATION_JSON)), handlers::handleCreateUser)
                .andRoute(GET(USER_RETRIEVE), handlers::handleRetrieveUser)
                .andRoute(GET(USER_ACTIVATE_ID), handlers::handleActivateUser)
                .andRoute(DELETE(USER_DELETE_ID), handlers::handleDeleteUser)
                .andRoute(PUT(USER_UPDATE).and(accept(MediaType.APPLICATION_JSON)), handlers::handleUpdateUser)
                .andRoute(PUT(USER_UPDATE_PASSWORD).and(accept(MediaType.APPLICATION_JSON)), handlers::handleUpdatePassword)
                .andRoute(GET("/test"), serverRequest -> ServerResponse.ok().body(Mono.just("Hello world.. Authorization server works fine!!!"), String.class));
    }
}
