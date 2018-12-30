package org.myaldoc.gestion.documents.service.services.impl;

import org.apache.commons.io.IOUtils;
import org.myaldoc.gestion.documents.service.models.Fichier;
import org.myaldoc.gestion.documents.service.repositories.FichierRepository;
import org.myaldoc.gestion.documents.service.services.GestionDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Objects;

/**
 * @Project MYALDOC
 * @Author Henri Joel SEDJAME
 * @Date 27/12/2018
 * @Class purposes : .......
 */
@Service
public class FichierServiceImpl implements GestionDocumentService {

    //********************************************************************************************************************
    // ATTRIBUTS
    //********************************************************************************************************************
    private final FichierRepository fichierRepository;

    //********************************************************************************************************************
    // CONSTRUCTEUR
    //********************************************************************************************************************
    @Autowired
    public FichierServiceImpl(FichierRepository fichierRepository) {
        this.fichierRepository = fichierRepository;
    }

    //********************************************************************************************************************
    // METHODES
    //********************************************************************************************************************
    /**
     * SAUVEGARDER FICHIER
     *
     * @param file
     * @return
     */
    @Override
    public Fichier sauvegarderFichier(MultipartFile file) throws IOException {
        return this.fichierRepository.save(
                Fichier.builder()
                    .name(StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename())))
                    .type(file.getContentType())
                    .content(Base64.getEncoder().withoutPadding().encodeToString(IOUtils.toByteArray(file.getInputStream())))
                    .build()
        );
    }

    /**
     * RECUPERER FICHIER
     *
     * @param fichierId
     * @return
     */
    @Override
    public Fichier recupererFichier(String fichierId) {
        return this.fichierRepository.findById(fichierId).orElseThrow();
    }

}
