package com.example.icebutler_server.global.util;

import com.example.icebutler_server.alarm.dto.FcmMessage;
import com.example.icebutler_server.global.exception.BaseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

import static com.example.icebutler_server.global.exception.ReturnCode.FIREBASE_SERVER_ERROR;

@Slf4j
@Component
@RequiredArgsConstructor
public class FcmUtils {
    public static final String FIREBASE_CONFIG_PATH = "firebase/firebase_service_key.json";
    public static final String NOTIFICATION_SCOPE = "https://www.googleapis.com/auth/cloud-platform";
    public static final String MEDIA_TYPE_JSON_UTF_8 = "application/json; charset=utf-8";
    public static final String CONTENT_TYPE_JSON_UTF_8 = "application/json; UTF-8";
    private static final String API_URL = "https://fcm.googleapis.com/v1/projects/icebutler-46914/messages:send";

    private final ObjectMapper objectMapper;

    public void sendMessage(String targetToken, String title, String body) {
        String message = makeMessage(targetToken, title, body);
        try {
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = RequestBody.create(message, MediaType.get(MEDIA_TYPE_JSON_UTF_8));
            Request request = new Request.Builder()
                    .url(API_URL)
                    .post(requestBody)
                    .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                    .addHeader(HttpHeaders.CONTENT_TYPE, CONTENT_TYPE_JSON_UTF_8)
                    .build();

            Response response = client.newCall(request).execute();
            log.info("알림 발신: ", response.body().toString());
        } catch (IOException e) {
            throw new BaseException(FIREBASE_SERVER_ERROR);
        }
    }

    public String makeMessage(String targetToken, String title, String body) {
        try {
            FcmMessage message = FcmMessage.makeMessage(targetToken, title, body);
            return objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            throw new BaseException(FIREBASE_SERVER_ERROR);
        }
    }

    private String getAccessToken() throws IOException {
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(FIREBASE_CONFIG_PATH).getInputStream())
                .createScoped(List.of(NOTIFICATION_SCOPE));
        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }

}
