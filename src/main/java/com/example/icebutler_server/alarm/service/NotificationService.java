package com.example.icebutler_server.alarm.service;

import com.example.icebutler_server.user.entity.User;

import java.io.IOException;

public interface NotificationService {
    void sendWithdrawalAlarm(User user, String fridgeName) throws IOException;
    void sendJoinFridgeAlarm(User user, String fridgeName) throws IOException;
    void sendShelfLifeAlarm(User user, String fridgeName, String foodName) throws IOException;

}
