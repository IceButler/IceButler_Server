package com.example.icebutler_server.global.feign.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class UserReq {
    private Long userIdx;
    private String nickname;
    private String profileImgKey;
    private String email;

    @Builder
    public UserReq(Long userIdx, String nickname, String profileImgKey, String email) {
        this.userIdx = userIdx;
        this.nickname = nickname;
        this.profileImgKey = profileImgKey;
        this.email = email;
    }
}
