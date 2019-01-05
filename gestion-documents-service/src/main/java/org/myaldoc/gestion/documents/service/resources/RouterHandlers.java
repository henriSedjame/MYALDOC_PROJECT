package org.myaldoc.gestion.documents.service.resources;

import org.myaldoc.gestion.documents.service.models.Fichier;
import org.myaldoc.gestion.documents.service.services.GestionDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.io.IOException;
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
    @Autowired
    private GestionDocumentService fichierService;

    //********************************************************************************************************************
    // METHODES
    //********************************************************************************************************************

    Mono<ServerResponse> handleSaveFile(ServerRequest request){

        return request.body(BodyExtractors.toMultipartData())
                .flatMap(file -> {
                    final Map<String, Part> stringPartMap = file.toSingleValueMap();
                    final FilePart file1 = (FilePart) stringPartMap.get("file");
                    try {
                        return this.fichierService.saveFichier(file1)
                                .flatMap(fichier -> ServerResponse.ok().body(Mono.just(fichier), Fichier.class))
                                .onErrorResume(RouterHandlers::handleError);
                    } catch (IOException e) {
                        return handleError(e);
                    }
                })
                .onErrorResume(RouterHandlers::handleError);
    }

    Mono<ServerResponse> handleRetrieveFichier(ServerRequest request){
        final String id = request.pathVariable("id");
        return this.fichierService.recupererFichier(id)
                .flatMap(fichier -> ServerResponse.ok().body(Mono.just(fichier), Fichier.class))
                .onErrorResume(RouterHandlers::handleError);
    }

    //********************************************************************************************************************
    // METHODES PRIVATE
    //********************************************************************************************************************

    private static Mono<? extends ServerResponse> handleError(Throwable error) {
        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN).syncBody(error.getMessage());
    }

}
