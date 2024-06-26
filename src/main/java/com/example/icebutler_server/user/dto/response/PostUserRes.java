package com.example.icebutler_server.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import static com.example.icebutler_server.global.util.Constant.COMMA;

@Schema(name = "PostUserRes", description = "유저 회원가입 정보")
@Data
public class PostUserRes {
    @Schema(description = "액세스 토큰", example = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBQ0NFU1MtVE9LRU4iLCJpYXQiOjE3MTk0MTAyMzYsInVzZXJJZHgiOjEsIm5pY2tuYW1lIjoi7IaM7KCVIiwiZXhwIjoxNzIwNjE5ODM2fQ.X8FMuGXaI0crDWgyyXSb6IfuaSLZ8NMiRCSfc-NIryc")
    private String accessToken;
    @Schema(description = "리프레쉬 토큰", example = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBQ0NFU1MtVE9LRU4iLCJpYXQiOjE3MTk0MTAyMzYsInVzZXJJZHgiOjEsIm5pY2tuYW1lIjoi7IaM7KCVIiwiZXhwIjaxNzIwNjE5ODM2fQ.X8FMuGX_I0crDWgyyXSb6IfuaSLZ8NMiRCSfc-NIryc")
    private String refreshToken;

    @Builder
    public PostUserRes(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static PostUserRes toDto(String token) {
        String accessToken = token.split(COMMA)[0];
        String refreshToken = token.split(COMMA)[1];

        return PostUserRes.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
