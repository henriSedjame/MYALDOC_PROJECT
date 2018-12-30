package org.myaldoc.gestion.documents.service.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @Project MYALDOC
 * @Author Henri Joel SEDJAME
 * @Date 26/12/2018
 * @Class purposes : .......
 */
public interface DocumentStorageService {

    /**
     * STOCKER UN DOCUMENT
     * @param file
     * @return
     */
    Mono<String> storeDocument(MultipartFile file);

    /**
     * STOCKER PLUSIEURS DOCUMENTS
     * @param files
     * @return
     */
    Flux<String> storeDocuments(MultipartFile[] files);

    /**
     * RECUPERER UN DOCUMENT
     * @param documentId
     * @return
     */
    Mono<Resource> loadDocumentAsResource(String documentId);

    /**
     *
     * @param documentIds
     * @return
     */
    Flux<Resource> loadDocumentsAsResources(String[] documentIds);
}
