package org.myaldoc.gestion.documents.service.services;

import org.myaldoc.gestion.documents.service.models.Fichier;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

import java.io.IOException;

/**
 * @Project MYALDOC
 * @Author Henri Joel SEDJAME
 * @Date 27/12/2018
 * @Class purposes : .......
 */
public interface GestionDocumentService {


    /**
     * SAUVEGARDER FICHIER
     * @param file
     * @return
     */
    Mono<Fichier> saveFichier(FilePart file) throws IOException;

    /**
     * RECUPERER FICHIER
     * @param fichierId
     * @return
     */
    Mono<Fichier> recupererFichier(String fichierId);

}
