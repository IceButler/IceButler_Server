package com.example.icebutler_server.user.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MyNotificationRes {

    private String pushNotificationType;
    private String notificationInfo;
    private LocalDateTime createdAt;

    @Builder
    public MyNotificationRes(String pushNotificationType, String notificationInfo, LocalDateTime createdAt) {
        this.pushNotificationType = pushNotificationType;
        this.notificationInfo = notificationInfo;
        this.createdAt = createdAt;
    }
}
