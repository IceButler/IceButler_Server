package com.example.icebutler_server.global.feign.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class UserReq {
    private Long userIdx;
    private String nickname;
    private String profileImgKey;

    @Builder
    public UserReq(Long userIdx, String nickname, String profileImgKey) {
        this.userIdx = userIdx;
        this.nickname = nickname;
        this.profileImgKey = profileImgKey;
    }
}
