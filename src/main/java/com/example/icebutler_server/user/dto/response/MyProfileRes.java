package com.example.icebutler_server.user.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MyProfileRes {

    private Long userIdx;
    private String nickName;
    private String profileImage;

    @Builder
    public MyProfileRes(Long userIdx, String nickName, String profileImage) {
        this.userIdx = userIdx;
        this.nickName = nickName;
        this.profileImage = profileImage;
    }
}
