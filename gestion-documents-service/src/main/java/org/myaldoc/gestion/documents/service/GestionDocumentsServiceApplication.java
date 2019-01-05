package org.myaldoc.gestion.documents.service;

import org.myaldoc.reactive.security.resource.server.configuration.annotations.EnableReactiveResourceServerSecurity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableReactiveResourceServerSecurity
public class GestionDocumentsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionDocumentsServiceApplication.class, args);
    }
}

