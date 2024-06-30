package com.example.icebutler_server.user.dto.response;

import com.example.icebutler_server.global.util.AwsS3ImageUrlUtil;
import com.example.icebutler_server.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@RequiredArgsConstructor
@Schema(name = "MyProfileRes", description = "유저 프로필 조회 정보")
public class MyProfileRes {
    @Schema(name = "userIdx", description = "유저 ID")
    private Long userIdx;
    @Schema(name = "nickname", description = "유저 닉네임")
    private String nickname;
    @Schema(name = "profileImgUrl", description = "유저 프로필 이미지 URL")
    private String profileImgUrl;
    @Schema(name = "email", description = "유저 이메일")
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
                .userIdx(user.getId())
                .nickname(user.getNickname())
                .email(user.getEmail());

        if (user.getProfileImgKey() != null) {
            builder.profileImgUrl(AwsS3ImageUrlUtil.toUrl(user.getProfileImgKey()));
        }

        return builder.build();
    }
}
