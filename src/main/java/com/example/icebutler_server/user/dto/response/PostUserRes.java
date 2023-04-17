package com.example.icebutler_server.user.dto.response;

import lombok.Data;

@Data
public class PostUserRes {
    private String accessToken;
    private String refreshToken;

    public static PostUserRes toDto(String token) {
        PostUserRes postUserRes = new PostUserRes();
        postUserRes.accessToken = token.split(",")[0];
        postUserRes.refreshToken = token.split(",")[1];
        return postUserRes;
    }
}
