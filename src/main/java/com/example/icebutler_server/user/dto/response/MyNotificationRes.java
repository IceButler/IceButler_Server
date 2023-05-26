package com.example.icebutler_server.user.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MyNotificationRes {

    private String pushNotificationType;
    private String notificationInfo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime createdAt;

    @Builder
    public MyNotificationRes(String pushNotificationType, String notificationInfo, LocalDateTime createdAt) {
        this.pushNotificationType = pushNotificationType;
        this.notificationInfo = notificationInfo;
        this.createdAt = createdAt;
    }
}
