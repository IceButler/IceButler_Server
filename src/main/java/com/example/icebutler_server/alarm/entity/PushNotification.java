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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PushNotificationType pushNotificationType;

    @Column(nullable = false)
    private String notificationInfo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @Column(nullable = false)
    @JoinColumn(name="userIdx")
    private User user;

    @Builder
    public PushNotification(PushNotificationType pushNotificationType, String notificationInfo, User user) {
        this.pushNotificationType = pushNotificationType;
        this.notificationInfo = notificationInfo;
        this.user = user;
    }
}
