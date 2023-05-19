package com.example.icebutler_server.admin.dto.response;

import lombok.Builder;
import lombok.Data;

import static com.example.icebutler_server.global.util.Constant.COMMA;

@Data
public class PostAdminRes {
    private String accessToken;
    private String refreshToken;

    @Builder
    public PostAdminRes(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static PostAdminRes toDto(String token) {
        String accessToken = token.split(COMMA)[0];
        String refreshToken = token.split(COMMA)[1];

        return PostAdminRes.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
