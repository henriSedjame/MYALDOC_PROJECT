package org.myaldoc.gestion.documents.service;

import org.myaldoc.security.core.configuration.annotations.MyaldocOauth2ResourceServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MyaldocOauth2ResourceServer
public class GestionDocumentsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionDocumentsServiceApplication.class, args);
    }
}

