package com.example.icebutler_server.alarm.service;

import com.example.icebutler_server.alarm.dto.FcmMessage;
import com.example.icebutler_server.alarm.dto.assembler.NotificationAssembler;
import com.example.icebutler_server.alarm.repository.PushNotificationRepository;
import com.example.icebutler_server.global.util.Constant;
import com.example.icebutler_server.user.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.json.JsonParseException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationServiceImpl implements NotificationService {
    private final String API_URL = "https://fcm.googleapis.com/v1/projects/icebutler-46914/messages:send";
    private final ObjectMapper objectMapper;
    private final PushNotificationRepository notificationRepository;
    private final NotificationAssembler notificationAssembler;

    // TODO 냉장고 유저 탈퇴 로직 리팩 후 호출
    @Transactional
    @Override
    public void sendWithdrawalAlarm(User user, String fridgeName) throws JsonParseException, IOException {
        String messageBody = fridgeName+"에서 탈퇴되었습니다.";
        if(user.getFcmToken()!=null){
            FcmMessage message = FcmMessage.makeMessage(user.getFcmToken(), Constant.PushNotification.FRIDGE, messageBody);
            Response response = sendMessage(objectMapper.writeValueAsString(message));
            System.out.println(response.body().string()); // TODO 프론트와 테스트 확인 후 출력문 삭제
            this.notificationRepository.save(this.notificationAssembler.toEntity(Constant.PushNotification.FRIDGE, messageBody, user));
        }
    }
    // TODO 냉장고 유저 초대 리펙 후 호출
    @Transactional
    @Override
    public void sendJoinFridgeAlarm(User user, String fridgeName) throws IOException {
        String messageBody = fridgeName+"에서 초대되었습니다.";
        if(user.getFcmToken()!=null) {
            FcmMessage message = FcmMessage.makeMessage(user.getFcmToken(), Constant.PushNotification.FRIDGE, messageBody);
            Response response = sendMessage(objectMapper.writeValueAsString(message));
            System.out.println(response.body().string()); // TODO 프론트와 테스트 확인 후 출력문 삭제
            this.notificationRepository.save(this.notificationAssembler.toEntity(Constant.PushNotification.FRIDGE, messageBody, user));
        }
    }

    @Transactional
    @Override
    public void sendShelfLifeAlarm(User user, String fridgeName, String foodName) throws IOException {
        String messageBody = foodName+" 소비기한이 임박해요!";
        if(user.getFcmToken()!=null) {
            FcmMessage message = FcmMessage.makeMessage(user.getFcmToken(), fridgeName, messageBody);
            Response response = sendMessage(objectMapper.writeValueAsString(message));
            System.out.println(response.body().string()); // TODO 프론트와 테스트 확인 후 출력문 삭제
            this.notificationRepository.save(this.notificationAssembler.toEntity(Constant.PushNotification.FRIDGE, messageBody, user));
        }
    }


    @NotNull
    private Response sendMessage(String message) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message,
                MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        return client.newCall(request).execute();
    }

    private String getAccessToken() throws IOException {
        String firebaseConfigPath = "firebase/firebase_service_key.json";
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));
        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }
}