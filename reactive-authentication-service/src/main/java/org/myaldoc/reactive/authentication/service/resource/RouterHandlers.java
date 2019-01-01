package org.myaldoc.reactive.authentication.service.resource;

import org.myaldoc.reactive.authentication.service.endpoints.AuthRequest;
import org.myaldoc.reactive.authentication.service.endpoints.AuthResponse;
import org.myaldoc.reactive.security.core.jwt.JwtUtils;
import org.myaldoc.reactive.security.core.models.User;
import org.myaldoc.reactive.security.core.services.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @Project MYALDOC_PORJECT
 * @Author Henri Joel SEDJAME
 * @Date 01/01/2019
 * @Class purposes : .......
 */
@Component
public class RouterHandlers {

    //********************************************************************************************************************
    // ATTRIBUTS
    //********************************************************************************************************************

    @Autowired
    private ConnectionService connectionService;

    @Autowired
    private JwtUtils jwtUtils;

    //********************************************************************************************************************
    // METHODES
    //********************************************************************************************************************

    Mono<ServerResponse> handleLogin(ServerRequest request){
        return request.bodyToMono(AuthRequest.class)
                .flatMap(authRequest -> this.connectionService.retrieveUser(authRequest.getUsername())
                                            .flatMap(user -> ServerResponse.ok()
                                                    .body(Mono.just(new AuthResponse(jwtUtils.generateToken(user))), AuthResponse.class)
                                            ).onErrorResume(RouterHandlers::handleError)
                );
    }

    Mono<ServerResponse> handleCreateUser(ServerRequest request){
        return request.bodyToMono(User.class)
                .flatMap(user -> this.connectionService.saveUser(user)
                        .flatMap(us -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(us), User.class))
                        .onErrorResume(RouterHandlers::handleError)
                );
    }

    Mono<ServerResponse> handleDeleteUser(ServerRequest request){
        final String id = request.pathVariable("id");
        return this.connectionService.deleteUser(id)
                .flatMap(aVoid -> ServerResponse.ok().syncBody(aVoid))
                .onErrorResume(RouterHandlers::handleError);
    }

    Mono<ServerResponse> handleUpdatePassword(ServerRequest request){
        return request.bodyToMono(AuthRequest.class)
                .flatMap(authRequest -> this.connectionService.changePassword(
                                            User.builder()
                                                    .username(authRequest.getUsername())
                                                    .password(authRequest.getPassword())
                                                    .build()
                        ).flatMap(user -> ServerResponse.ok()
                                .body(Mono.just(new AuthResponse(jwtUtils.generateToken(user))), AuthResponse.class))
                        .onErrorResume(RouterHandlers::handleError)
                );
    }

    Mono<ServerResponse> handleUpdateUser(ServerRequest request){
        return request.bodyToMono(User.class)
                .flatMap(user -> this.connectionService.updateUser(user)
                                        .flatMap(u-> ServerResponse.ok()
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .body(Mono.just(new AuthResponse(jwtUtils.generateToken(user))), AuthResponse.class))
                                        .onErrorResume(RouterHandlers::handleError)
                );
    }

    Mono<ServerResponse> handleActivateUser(ServerRequest request){
        final String id = request.pathVariable("id");
        return this.connectionService.activateUser(id)
                .flatMap(user -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(user), User.class))
                .onErrorResume(RouterHandlers::handleError);
    }

    //********************************************************************************************************************
    // METHODES PRIVATE
    //********************************************************************************************************************

    private static Mono<? extends ServerResponse> handleError(Throwable error) {
        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN).syncBody(error.getMessage());
    }
}
