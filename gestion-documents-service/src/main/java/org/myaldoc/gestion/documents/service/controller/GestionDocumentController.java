package org.myaldoc.gestion.documents.service.controller;

import org.myaldoc.gestion.documents.service.exceptions.GestionDocumentExceptionBuilder;
import org.myaldoc.gestion.documents.service.exceptions.GestionDocumentExceptionMessages;
import org.myaldoc.gestion.documents.service.services.GestionDocumentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Project MYALDOC
 * @Author Henri Joel SEDJAME
 * @Date 26/12/2018
 * @Class purposes : .......
 */
@RestController
public class GestionDocumentController {

    //********************************************************************************************************************
    // ATTRIBUTS
    //********************************************************************************************************************
    private final GestionDocumentService fichierService;
    private final GestionDocumentExceptionBuilder exceptionBuilder;
    private final GestionDocumentExceptionMessages exceptionMessages;

    //********************************************************************************************************************
    // CONSTRUCTEUR
    //********************************************************************************************************************
    public GestionDocumentController(GestionDocumentService fichierService,
                                     GestionDocumentExceptionBuilder exceptionBuilder,
                                     GestionDocumentExceptionMessages exceptionMessages) {
        this.fichierService = fichierService;
        this.exceptionBuilder = exceptionBuilder;
        this.exceptionMessages = exceptionMessages;
    }

    //********************************************************************************************************************
    // METHODES
    //********************************************************************************************************************


    @PostMapping(value = "/saveFichier")
    public ResponseEntity<Object> saveFichier(@RequestParam("file") MultipartFile file){
        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(this.fichierService.sauvegarderFichier(file));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("");
        }
    }

    @GetMapping("/fichier/{fichierId}")
    public ResponseEntity<Object> recupererFichier(@PathVariable String fichierId){
        return ResponseEntity.ok()
                .body(this.fichierService.recupererFichier(fichierId));
    }

}
