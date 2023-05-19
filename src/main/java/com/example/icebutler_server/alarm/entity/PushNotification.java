package com.example.icebutler_server.alarm.entity;

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

    @Column(nullable = false)
    private String pushNotificationType;

    @Column(nullable = false)
    private String notificationInfo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="userIdx")
    private User user;

    @Builder
    public PushNotification(String pushNotificationType, String notificationInfo, User user) {
        this.pushNotificationType = pushNotificationType;
        this.notificationInfo = notificationInfo;
        this.user = user;
    }
}
