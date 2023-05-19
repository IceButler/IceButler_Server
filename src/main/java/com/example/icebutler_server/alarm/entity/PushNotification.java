package com.example.icebutler_server.alarm.entity;

import com.example.icebutler_server.alarm.PushNotificationType;
import com.example.icebutler_server.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class PushNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long notificationIdx;

    private String notificationId;

    @Enumerated(EnumType.STRING)
    private PushNotificationType pushNotificationType;
    private String notificationInfo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="userIdx")
    private User user;

    @Builder
    public PushNotification(String notificationId, PushNotificationType pushNotificationType, String notificationInfo, User user) {
        this.notificationId = notificationId;
        this.pushNotificationType = pushNotificationType;
        this.notificationInfo = notificationInfo;
        this.user = user;
    }
}
