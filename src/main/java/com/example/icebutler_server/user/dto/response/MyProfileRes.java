package com.example.icebutler_server.user.dto.response;

import com.example.icebutler_server.global.util.AwsS3ImageUrlUtil;
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
        MyProfileResBuilder builder = MyProfileRes.builder()
                .userIdx(user.getUserIdx())
                .nickname(user.getNickname())
                .email(user.getEmail());

        if (user.getProfileImgKey() != null) {
            builder.profileImgUrl(AwsS3ImageUrlUtil.toUrl(user.getProfileImgKey()));
        }

        return builder.build();
    }
}
