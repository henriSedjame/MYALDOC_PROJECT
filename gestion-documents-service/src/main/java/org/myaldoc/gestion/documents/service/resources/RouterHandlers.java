package org.myaldoc.gestion.documents.service.resources;

import org.myaldoc.gestion.documents.service.exceptions.GestionDocumentException;
import org.myaldoc.gestion.documents.service.models.Fichier;
import org.myaldoc.gestion.documents.service.services.GestionDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * @Project MYALDOC_PORJECT
 * @Author Henri Joel SEDJAME
 * @Date 05/01/2019
 * @Class purposes : .......
 */
@Component
public class RouterHandlers {

    //********************************************************************************************************************
    // ATTRIBUTS
    //********************************************************************************************************************
    private final GestionDocumentService fichierService;

    //********************************************************************************************************************
    // CONSTRUCTEUR
    //********************************************************************************************************************

    public RouterHandlers(GestionDocumentService fichierService) {
        this.fichierService = fichierService;
    }

    //********************************************************************************************************************
    // METHODES
    //********************************************************************************************************************

    Mono<ServerResponse> handleSaveFile(ServerRequest request){

        return request.body(BodyExtractors.toMultipartData())
                .flatMap(multiPart -> {
                    final Map<String, Part> stringPartMap = multiPart.toSingleValueMap();
                    final FilePart file = (FilePart) stringPartMap.get("file");
                    try {
                        return this.fichierService.saveFichier(file)
                                .flatMap(fichier -> ServerResponse.ok().body(Mono.just(fichier), Fichier.class))
                                .onErrorResume(RouterHandlers::handleError);
                    } catch (GestionDocumentException e) {
                        return handleError(e);
                    }
                })
                .onErrorResume(RouterHandlers::handleError);
    }

    Mono<ServerResponse> handleRetrieveFile(ServerRequest request){
        try {
            final String id = request.pathVariable("id");
            return this.fichierService.recupererFichier(id)
                    .flatMap(fichier -> ServerResponse.ok().body(Mono.just(fichier), Fichier.class))
                    .onErrorResume(RouterHandlers::handleError);
        } catch (GestionDocumentException e) {
            return handleError(e);
        }
    }
    
    Mono<ServerResponse> handleDeleteFile(ServerRequest request){
        try {
            final String id = request.pathVariable("id");
            return this.fichierService.deleteFichier(id)
                    .flatMap(aVoid -> ServerResponse.ok().syncBody(aVoid))
                    .onErrorResume(RouterHandlers::handleError);
        } catch (GestionDocumentException e) {
            return handleError(e);
        }
    }

    //********************************************************************************************************************
    // METHODES PRIVATE
    //********************************************************************************************************************

    private static Mono<ServerResponse> handleError(Throwable error) {
        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).syncBody(error);
    }

}
