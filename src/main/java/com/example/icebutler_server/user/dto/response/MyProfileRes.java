package com.example.icebutler_server.user.dto.response;

import lombok.*;

@Data
@RequiredArgsConstructor
public class MyProfileRes {

    private Long userIdx;
    private String nickName;
    private String profileImage;
    private String email;

    @Builder
    public MyProfileRes(Long userIdx, String nickName, String profileImage, String email) {
        this.userIdx = userIdx;
        this.nickName = nickName;
        this.profileImage = profileImage;
        this.email=email;
    }
}
