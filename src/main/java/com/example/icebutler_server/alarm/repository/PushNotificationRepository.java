package com.example.icebutler_server.alarm.repository;

import com.example.icebutler_server.alarm.entity.PushNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PushNotificationRepository extends JpaRepository<PushNotification, Long> {
}
