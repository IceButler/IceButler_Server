package com.example.icebutler_server.alarm.dto.assembler;

import com.example.icebutler_server.alarm.PushNotificationType;
import com.example.icebutler_server.alarm.entity.PushNotification;
import com.example.icebutler_server.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationAssembler {
    public PushNotification toEntity(String notificationId, PushNotificationType pushNotificationType, String messageBody, User user) {
        return PushNotification.builder()
                .notificationId(notificationId)
                .pushNotificationType(pushNotificationType)
                .notificationInfo(messageBody)
                .user(user)
                .build();
    }
}
