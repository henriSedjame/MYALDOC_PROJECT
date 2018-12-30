package org.myaldoc.gestion.documents.service.services;

import org.myaldoc.gestion.documents.service.models.Fichier;
import org.springframework.web.multipart.MultipartFile;

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
    Fichier sauvegarderFichier(MultipartFile file) throws IOException;

    /**
     * RECUPERER FICHIER
     * @param fichierId
     * @return
     */
    Fichier recupererFichier(String fichierId);

}
