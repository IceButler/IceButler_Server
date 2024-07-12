package com.example.icebutler_server.alarm.entity;

import com.example.icebutler_server.global.entity.BaseEntity;
import com.example.icebutler_server.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class PushNotification extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String pushNotificationType;

    @Column(nullable = false)
    private String notificationInfo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="user_id")
    private User user;

    @Builder
    public PushNotification(String pushNotificationType, String notificationInfo, User user) {
        this.pushNotificationType = pushNotificationType;
        this.notificationInfo = notificationInfo;
        this.user = user;
    }

    public static PushNotification toEntity(String pushNotificationType, String messageBody, User user) {
        return PushNotification.builder()
                .pushNotificationType(pushNotificationType)
                .notificationInfo(messageBody)
                .user(user)
                .build();
    }
}
