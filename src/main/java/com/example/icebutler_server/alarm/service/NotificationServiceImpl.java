package com.example.icebutler_server.alarm.service;

import com.example.icebutler_server.alarm.dto.FcmMessage;
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

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final String API_URL = "https://fcm.googleapis.com/v1/projects/icebutler-46914/messages:send";
    private final ObjectMapper objectMapper;

    // TODO 냉장고 유저 탈퇴 로직 리팩 후 호출 추가
    @Override
    public void sendWithdrawalAlarm(User user, String fridgeName) throws JsonParseException, IOException {
        FcmMessage message = FcmMessage.makeMessage(user.getFcmToken(), "냉장고", fridgeName+"에서 탈퇴되었습니다.");
        Response response = sendMessage(objectMapper.writeValueAsString(message));
        System.out.println(response.body().string()); // TODO 프론트와 테스트 확인 후 출력문 삭제
    }
    // TODO 냉장고 유저 초대 리펙 후 호출 추가

    @Override
    public void sendJoinFridgeAlarm(User user, String fridgeName) throws IOException {
        FcmMessage message = FcmMessage.makeMessage(user.getFcmToken(), "냉장고", fridgeName+"에 초대 되었습니다.");
        Response response = sendMessage(objectMapper.writeValueAsString(message));
        System.out.println(response.body().string()); // TODO 프론트와 테스트 확인 후 출력문 삭제
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