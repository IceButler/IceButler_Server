package com.example.icebutler_server.global.feign.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class UserReq {
    private Long userId;
    private String nickname;
    private String profileImgKey;
    private String email;

    @Builder
    public UserReq(Long userId, String nickname, String profileImgKey, String email) {
        this.userId = userId;
        this.nickname = nickname;
        this.profileImgKey = profileImgKey;
        this.email = email;
    }
}
