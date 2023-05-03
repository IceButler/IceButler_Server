package com.example.icebutler_server.user.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Builder
public class NickNameRes {
    private final String nickname;


    public static NickNameRes toDto(String nickname){
        return NickNameRes.builder()
                .nickname(nickname)
                .build();
    }
}
