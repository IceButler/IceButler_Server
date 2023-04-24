package com.example.icebutler_server.global.feign.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class UserReq {
    private Long userIdx;
    private String nickname;
    private String profileImg;

    @Builder
    public UserReq(Long userIdx, String nickname, String profileImg) {
        this.userIdx = userIdx;
        this.nickname = nickname;
        this.profileImg = profileImg;
    }
}
