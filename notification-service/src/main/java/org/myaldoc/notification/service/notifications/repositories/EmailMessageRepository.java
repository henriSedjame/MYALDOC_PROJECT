package org.myaldoc.notification.service.notifications.repositories;

import org.myaldoc.notification.service.notifications.models.EmailMessage;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;


/**
 * @Project MYALDOC
 * @Author Henri Joel SEDJAME
 * @Date 29/11/2018
 * @Class purposes : .......
 */
public interface EmailMessageRepository extends ReactiveMongoRepository<EmailMessage, String> {
}
