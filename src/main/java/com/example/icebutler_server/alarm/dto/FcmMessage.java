package com.example.icebutler_server.alarm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class FcmMessage {
    private boolean validateOnly;
    private Message message;

    public static FcmMessage makeMessage(String targetToken, String title, String body) {
        return FcmMessage.builder()
                .message(Message.toEntity(targetToken, title, body))
                .validateOnly(false)
                .build();
    }

}
