package com.example.icebutler_server.alarm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class Alarm {
    private String title;
    private String body;
    private String image;

    public static Alarm toEntity(String title, String body) {
        return Alarm.builder()
                .title(title)
                .body(body)
                .image(null)
                .build();
    }
}