package org.myaldoc.reactive.security.resource.server.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @Project MYALDOC_PORJECT
 * @Author Henri Joel SEDJAME
 * @Date 01/01/2019
 * @Class purposes : .......
 */
@SpringBootApplication
public class ReactiveSecurityResourceServerConfigurationApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReactiveSecurityResourceServerConfigurationApplication.class, args);
    }


}
