package com.example.icebutler_server.user.dto.response;

import com.example.icebutler_server.alarm.entity.PushNotification;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

@Data
@Schema(name = "MyNotificationRes", description = "유저 알림 목록 정보")
public class MyNotificationRes {
    @Schema(name = "pushNotificationType", description = "알림 종류")
    private String pushNotificationType;
    @Schema(name = "notificationInfo", description = "알림 내용")
    private String notificationInfo;
    @Schema(name = "createdAt", description = "알림 일자", example = "2024-01-01")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime createdAt;

    @Builder
    public MyNotificationRes(String pushNotificationType, String notificationInfo, LocalDateTime createdAt) {
        this.pushNotificationType = pushNotificationType;
        this.notificationInfo = notificationInfo;
        this.createdAt = createdAt;
    }

    public static Page<MyNotificationRes> toUserNotificationList(Page<PushNotification> notifications) {
        return notifications.map(n -> MyNotificationRes.builder()
                .pushNotificationType(n.getPushNotificationType())
                .notificationInfo(n.getNotificationInfo())
                .createdAt(n.getCreatedAt())
                .build());
    }
}
