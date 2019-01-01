package org.myaldoc.reactive.security.core.services;

import org.myaldoc.reactive.security.core.models.User;


/**
 * @Project MYALDOC
 * @Author Henri Joel SEDJAME
 * @Date 09/12/2018
 * @Class purposes : .......
 */
public interface NotificationSender {

    void notifyAccountCreation(User user);

    void notifyAccountDeletion(User user);

    void notifyAccountActivation(User user);
}
