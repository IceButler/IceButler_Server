package com.example.icebutler_server.alarm.service;

import com.example.icebutler_server.user.entity.User;

import java.io.IOException;

public interface AlarmService {
    void sendWithdrawalAlarm(User user, String fridgeName) throws IOException;

}
