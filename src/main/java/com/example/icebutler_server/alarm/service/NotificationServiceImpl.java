package com.example.icebutler_server.alarm.service;

import com.example.icebutler_server.alarm.entity.PushNotification;
import com.example.icebutler_server.alarm.repository.PushNotificationRepository;
import com.example.icebutler_server.global.util.Constant;
import com.example.icebutler_server.global.util.FcmUtils;
import com.example.icebutler_server.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationServiceImpl implements NotificationService {

    private final PushNotificationRepository notificationRepository;
    private final FcmUtils fcmUtils;


    @Transactional
    @Override
    public void sendWithdrawalAlarm(User user, String fridgeName) {
        if (user.getFcmToken() == null) return;
        String messageBody = fridgeName + "에서 탈퇴되었습니다.";
        fcmUtils.sendMessage((user.getFcmToken()), Constant.PushNotification.FRIDGE, messageBody);
        this.notificationRepository.save(PushNotification.toEntity(Constant.PushNotification.FRIDGE, messageBody, user));
    }

    @Transactional
    @Override
    public void sendJoinFridgeAlarm(User user, String fridgeName) {
        if (user.getFcmToken() == null) return;
        String messageBody = fridgeName + "에서 초대되었습니다.";
        fcmUtils.sendMessage((user.getFcmToken()), Constant.PushNotification.FRIDGE, messageBody);
        this.notificationRepository.save(PushNotification.toEntity(Constant.PushNotification.FRIDGE, messageBody, user));
    }

    @Transactional
    @Override
    public void sendShelfLifeAlarm(User user, String fridgeName, String foodName) throws IOException {
        if (user.getFcmToken() == null) return;
        String messageBody = foodName + " 소비기한이 임박해요!";
        fcmUtils.sendMessage((user.getFcmToken()), fridgeName, messageBody);
        this.notificationRepository.save(PushNotification.toEntity(fridgeName, messageBody, user));
    }
}