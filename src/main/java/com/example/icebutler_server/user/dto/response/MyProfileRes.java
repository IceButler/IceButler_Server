package com.example.icebutler_server.user.dto.response;

import com.example.icebutler_server.user.entity.User;
import lombok.*;

@Data
@RequiredArgsConstructor
public class MyProfileRes {

    private Long userIdx;
    private String nickname;
    private String profileImgUrl;
    private String email;

    @Builder
    public MyProfileRes(Long userIdx, String nickname, String profileImgUrl, String email) {
        this.userIdx = userIdx;
        this.nickname = nickname;
        this.profileImgUrl = profileImgUrl;
        this.email = email;
    }

    public static MyProfileRes toDto(User user) {
        return MyProfileRes.builder()
                .userIdx(user.getUserIdx())
                .nickname(user.getNickname())
                .profileImgUrl(user.getProfileImgKey())
                .email(user.getEmail())
                .build();
    }
}
