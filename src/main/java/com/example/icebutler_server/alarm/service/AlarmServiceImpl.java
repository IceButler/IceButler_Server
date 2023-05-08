package com.example.icebutler_server.alarm.service;
import com.example.icebutler_server.user.entity.User;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AlarmServiceImpl implements AlarmService {

    public void sendWithdrawalAlarm(User user, String fridgeName) {

        String token = user.getFcmToken();
        Message message = Message.builder()
                .putData("title", "냉장고")
                .putData("content", fridgeName+"에서 탈퇴되었어요")
                .setToken(token)
                .build();
        send(message);
    }

    public void send(Message message) {
        FirebaseMessaging.getInstance().sendAsync(message);
    }

}
