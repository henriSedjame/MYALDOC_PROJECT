package org.myaldoc.reactive.authentication.service.resource;

import org.myaldoc.reactive.authentication.service.endpoints.AuthRequest;
import org.myaldoc.reactive.authentication.service.endpoints.AuthResponse;
import org.myaldoc.reactive.security.core.jwt.JwtUtils;
import org.myaldoc.reactive.security.core.models.User;
import org.myaldoc.reactive.security.core.services.ConnectionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    private final ConnectionService connectionService;

    private final JwtUtils jwtUtils;

    private final BCryptPasswordEncoder passwordEncoder;

    //********************************************************************************************************************
    // CONSTRUCTEUR
    //********************************************************************************************************************

    public RouterHandlers(ConnectionService connectionService,
                          JwtUtils jwtUtils,
                          BCryptPasswordEncoder passwordEncoder) {
        this.connectionService = connectionService;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }

    //********************************************************************************************************************
    // METHODES
    //********************************************************************************************************************

    Mono<ServerResponse> handleLogin(ServerRequest request){
        return request.bodyToMono(AuthRequest.class)
                .flatMap(authRequest -> this.connectionService.retrieveUser(authRequest.getUsername())
                                            .flatMap(user -> {
                                                final boolean matches = passwordEncoder.matches(authRequest.getPassword(), user.getPassword());
                                                if (matches) return ServerResponse.ok()
                                                                .body(Mono.just(new AuthResponse(jwtUtils.generateToken(user))), AuthResponse.class);
                                                else
                                                    return handleError(new BadCredentialsException("Identifiants incorrects"));
                                            }).onErrorResume(RouterHandlers::handleError)
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

    Mono<ServerResponse> handleRetrieveUser(ServerRequest request){
        final String username = request.pathVariable("username");
        return this.connectionService.retrieveUser(username)
                .flatMap(user -> ServerResponse.ok().body(Mono.just(user), User.class))
                .onErrorResume(RouterHandlers::handleError);

    }

    //********************************************************************************************************************
    // METHODES PRIVATE
    //********************************************************************************************************************

    private static Mono<? extends ServerResponse> handleError(Throwable error) {
        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).syncBody(error);
    }
}
