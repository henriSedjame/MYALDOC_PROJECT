package org.myaldoc.reactive.security.core.services.impl;

import org.myaldoc.core.messaging.Mail;
import org.myaldoc.reactive.security.core.messaging.EmailSource;
import org.myaldoc.reactive.security.core.models.User;
import org.myaldoc.reactive.security.core.services.NotificationSender;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * @Project MYALDOC
 * @Author Henri Joel SEDJAME
 * @Date 09/12/2018
 * @Class purposes : Envoyer des notifications
 */
@Component
public class NotificationSenderImpl implements NotificationSender {


    private static final String ACCOUNT_CREATION = "Création de compte";
    private static final String ACCOUNT_DELETION = "Suppression de compte";
    private static final String ACCOUNT_ACTIVATION = "Activation de compte";

    private EmailSource emailSource;

    public NotificationSenderImpl(EmailSource emailSource) {
        this.emailSource = emailSource;
    }

    /**
     * ENVOI DE NOTIFICATION DE CREATION D'UN COMPTE
     *
     * @param user
     */
    public void notifyAccountCreation(User user) {
        emailSource.accountCreationEmailOutput()
                .send(MessageBuilder
                        .withPayload(Mail
                                .builder()
                                .subject(ACCOUNT_CREATION)
                                .sentToName(user.getUsername())
                                .sentToEmail(user.getEmail())
                                .userActivationUri("http://localhost:9999/authorization-server/auth/user/activate/" + user.getId())
                                .build())
                        .build());
    }

    /**
     * ENVOI DE NOTIFICATION DE SUPPRESSION DE COMPTE
     *
     * @param user
     */
    @Override
    public void notifyAccountDeletion(User user) {
        emailSource.accountDeletionEmailOutput()
                .send(MessageBuilder
                        .withPayload(Mail
                                .builder()
                                .subject(ACCOUNT_DELETION)
                                .sentToName(user.getUsername())
                                .sentToEmail(user.getEmail())
                                .build())
                        .build());
    }

    /**
     * ENVOI DE NOTIFICATION D'ACTIVATION DE COMPTE
     *
     * @param user
     */
    @Override
    public void notifyAccountActivation(User user) {
        emailSource.accountActivationEmailOutput()
                .send(MessageBuilder
                        .withPayload(Mail
                                .builder()
                                .subject(ACCOUNT_ACTIVATION)
                                .sentToName(user.getUsername())
                                .sentToEmail(user.getEmail())
                                .build())
                        .build());

    }
}
