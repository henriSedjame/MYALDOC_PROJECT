package org.myaldoc.gestion.documents.service.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Project MYALDOC
 * @Author Henri Joel SEDJAME
 * @Date 24/12/2018
 * @Class purposes : .......
 */
@NoArgsConstructor
@Getter
@Setter
@Component
@ConfigurationProperties("error.messages")
public class GestionDocumentExceptionMessages {
    private String saveFileError;
    private String saveFileNull;
    private String fileIdNull;
    private String retrieveFileError;
    private String deleteFileError;
}
