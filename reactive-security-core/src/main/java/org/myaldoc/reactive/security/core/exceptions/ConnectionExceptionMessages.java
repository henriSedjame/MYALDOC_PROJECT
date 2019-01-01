package org.myaldoc.reactive.security.core.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Project MYALDOC
 * @Author Henri Joel SEDJAME
 * @Date 27/11/2018
 * @Class purposes : .......
 */
@NoArgsConstructor
@Getter
@Setter
@Component
@ConfigurationProperties("error.messages")
public class ConnectionExceptionMessages {

    private String defaultMessage;
    private String internalError;
    private String userNotFound;
    private String badCredentials;
    private String userAlreadyExist;
    private String accountDeletionError;
    private String userRetrievingError;
    private String userSavingError;
    private String userActivationError;
    private String userNotFoundForPasswordUpdating;

}
