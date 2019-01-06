package org.myaldoc.gestion.documents.service.services.impl;

import org.apache.commons.io.IOUtils;
import org.myaldoc.core.aspects.annotations.ExceptionBuilderClearBefore;
import org.myaldoc.core.io.InputStreamCollector;
import org.myaldoc.core.utils.RequireObjects;
import org.myaldoc.gestion.documents.service.exceptions.GestionDocumentException;
import org.myaldoc.gestion.documents.service.exceptions.GestionDocumentExceptionBuilder;
import org.myaldoc.gestion.documents.service.exceptions.GestionDocumentExceptionMessages;
import org.myaldoc.gestion.documents.service.models.Fichier;
import org.myaldoc.gestion.documents.service.repositories.FichierRepository;
import org.myaldoc.gestion.documents.service.services.GestionDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.text.MessageFormat;
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
    private final GestionDocumentExceptionBuilder exceptionBuilder;
    private final GestionDocumentExceptionMessages exceptionMessages;

    //********************************************************************************************************************
    // CONSTRUCTEUR
    //********************************************************************************************************************
    @Autowired
    public FichierServiceImpl(FichierRepository fichierRepository,
                              GestionDocumentExceptionBuilder exceptionBuilder,
                              GestionDocumentExceptionMessages exceptionMessages) {
        this.fichierRepository = fichierRepository;
        this.exceptionBuilder = exceptionBuilder;
        this.exceptionMessages = exceptionMessages;
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
    @ExceptionBuilderClearBefore
    public Mono<Fichier> saveFichier(FilePart file) throws GestionDocumentException {
        RequireObjects.requireNotNull(file, GestionDocumentException.class, this.exceptionMessages.getSaveFileNull());
        return Mono.defer(() -> {
            try {
                return file.content()
                        .collect(InputStreamCollector::new, (isc, dataBuffer) -> isc.collectInputStream(dataBuffer.asInputStream()))
                        .map(isc -> {
                            try {
                                return Fichier.builder()
                                        .name(StringUtils.cleanPath(Objects.requireNonNull(file.filename())))
                                        .type(this.getcontentType(file))
                                        .content(Base64.getEncoder().withoutPadding().encodeToString(IOUtils.toByteArray(isc.getInputStream())))
                                        .build();
                            } catch (IOException e) {
                                return null;
                            }
                        })
                        .flatMap(fichier -> {
                            if (Objects.nonNull(fichier)) return this.fichierRepository.save(fichier);
                            else return Mono.error(this.exceptionBuilder.buildException(MessageFormat.format(this.exceptionMessages.getSaveFileError(), file.filename()), null));
                        });
            }catch (Exception e) {
                return Mono.error(this.exceptionBuilder.buildException(MessageFormat.format(this.exceptionMessages.getRetrieveFileError(),file.filename()), e));
            }
        });
    }


    /**
     * RECUPERER FICHIER
     *
     * @param fichierId
     * @return
     */
    @Override
    @ExceptionBuilderClearBefore
    public Mono<Fichier> recupererFichier(String fichierId) throws GestionDocumentException {
        RequireObjects.requireNotNull(fichierId, GestionDocumentException.class , this.exceptionMessages.getFileIdNull());
        return Mono.defer( () -> {
            try{
                return this.fichierRepository.findById(fichierId);
            }catch (Exception e) {
                return Mono.error(this.exceptionBuilder.buildException(MessageFormat.format(this.exceptionMessages.getRetrieveFileError(), fichierId), e));
            }
        });
    }

    /**
     * SUPPRESSION D'UN FICHIER
     *
     * @param fichierId
     * @return
     */
    @Override
    @ExceptionBuilderClearBefore
    public Mono<Void> deleteFichier(String fichierId) throws GestionDocumentException {
        RequireObjects.requireNotNull(fichierId, GestionDocumentException.class , this.exceptionMessages.getSaveFileError());
        return Mono.defer(() -> {
            try{
               return this.fichierRepository.deleteById(fichierId);
            }catch(Exception e){
                return Mono.error(this.exceptionBuilder.buildException(MessageFormat.format(this.exceptionMessages.getDeleteFileError(), fichierId), e));
            }
        });
    }

    //********************************************************************************************************************
    // METHODES PRIVATE
    //********************************************************************************************************************

    private String getcontentType(Part file) {
        final MediaType contentType = Objects.requireNonNull(file).headers().getContentType();
        return contentType.getType().concat("/").concat(contentType.getSubtype());
    }

}
