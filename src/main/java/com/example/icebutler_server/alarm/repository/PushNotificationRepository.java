package com.example.icebutler_server.alarm.repository;

import com.example.icebutler_server.alarm.entity.PushNotification;
import com.example.icebutler_server.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PushNotificationRepository extends JpaRepository<PushNotification, Long> {
    Page<PushNotification> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);
}
