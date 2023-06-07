package com.example.icebutler_server.alarm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class Notification {
    private String title;
    private String body;
    private String image;
    private String sound;

    public static Notification toEntity(String title, String body) {
        return Notification.builder()
                .title(title)
                .body(body)
                .image(null)
                .sound("default")
                .build();
    }
}