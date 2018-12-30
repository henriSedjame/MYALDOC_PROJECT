package org.myaldoc.gestion.documents.service.services.impl;

import org.bson.types.ObjectId;
import org.myaldoc.core.aspects.annotations.ExceptionBuilderClearBefore;
import org.myaldoc.gestion.documents.service.exceptions.GestionDocumentExceptionBuilder;
import org.myaldoc.gestion.documents.service.exceptions.GestionDocumentExceptionMessages;
import org.myaldoc.gestion.documents.service.services.DocumentStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Project MYALDOC
 * @Author Henri Joel SEDJAME
 * @Date 26/12/2018
 * @Class purposes : .......
 */
@Service
public class DocumentStorageServiceImpl implements DocumentStorageService {

    //********************************************************************************************************************
    // ATTRIBUTS
    //********************************************************************************************************************
    private final GridFsTemplate gridFsTemplate;
    private final GestionDocumentExceptionBuilder exceptionBuilder;
    private final GestionDocumentExceptionMessages exceptionMessages;

    //********************************************************************************************************************
    // CONSTRUCTEUR
    //********************************************************************************************************************
    @Autowired
    public DocumentStorageServiceImpl(GridFsTemplate gridFsTemplate,
                                      GestionDocumentExceptionBuilder exceptionBuilder,
                                      GestionDocumentExceptionMessages exceptionMessages) {
        this.gridFsTemplate = gridFsTemplate;
        this.exceptionBuilder = exceptionBuilder;
        this.exceptionMessages = exceptionMessages;
    }

    //********************************************************************************************************************
    // METHODES
    //********************************************************************************************************************
    /**
     * STOCKER UN DOCUMENT
     *
     * @param file
     * @return
     */
    @Override
    @ExceptionBuilderClearBefore
    public Mono<String> storeDocument(MultipartFile file) {
        return Mono.defer(() -> {
            try{
                return Mono.just(gridFsTemplate.store(file.getInputStream(), file.getName(), file.getContentType()).toString());
            }catch (IOException e){
                return Mono.error(e);
            }
        });
    }

    /**
     * STOCKER PLUSIEURS DOCUMENTS
     *
     * @param files
     * @return
     */
    @Override
    public Flux<String> storeDocuments(MultipartFile[] files) {
        return Flux.mergeSequential(
                Arrays
                    .stream(files)
                    .map(this::storeDocument)
                    .collect(Collectors.toList())
        );
    }

    /**
     * RECUPERER UN DOCUMENT
     *
     * @param documentId
     * @return
     */
    @Override
    @ExceptionBuilderClearBefore
    public Mono<Resource> loadDocumentAsResource(String documentId) {
        return Mono.defer(() -> {
            try{
                return Mono.just(
                    this.gridFsTemplate.getResource(
                        Objects.requireNonNull(
                            gridFsTemplate.findOne(
                                new Query()
                                    .addCriteria(
                                        Criteria.where("_id").is(new ObjectId(documentId))
                                    )
                            )
                        )
                    ));
            }catch (Exception e){
               return Mono.error(e);
            }
        });
    }

    /**
     * @param documentIds
     * @return
     */
    @Override
    @ExceptionBuilderClearBefore
    public Flux<Resource> loadDocumentsAsResources(String[] documentIds) {
        return Flux.mergeSequential(
            Arrays
                .stream(documentIds)
                .map(this::loadDocumentAsResource)
                .collect(Collectors.toList())
        );
    }
}
