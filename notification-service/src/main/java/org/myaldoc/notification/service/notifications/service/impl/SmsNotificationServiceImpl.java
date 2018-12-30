package org.myaldoc.notification.service.notifications.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.myaldoc.core.messaging.Sms;
import org.myaldoc.notification.service.configuration.messaging.SmsSink;
import org.myaldoc.notification.service.notifications.service.NotificationService;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.stereotype.Service;

@Service
@EnableBinding(SmsSink.class)
@Slf4j
public class SmsNotificationServiceImpl implements NotificationService<Sms> {
}
