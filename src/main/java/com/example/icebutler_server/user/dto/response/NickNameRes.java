package com.example.icebutler_server.user.dto.response;

import com.example.icebutler_server.global.util.AwsS3ImageUrlUtil;
import com.example.icebutler_server.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "NickNameRes", description = "닉네임 유저 조회 정보")
public class NickNameRes {
    @Schema(name = "nickname", description = "닉네임")
    private  String nickname;
    @Schema(name = "userIdx", description = "유저 ID")
    private  Long userIdx;
    @Schema(name = "profileImgUrl", description = "유저 프로필 이미지 URL")
    private String profileImgUrl;


    public static NickNameRes toDto(User user){
        return NickNameRes.builder()
                .nickname(user.getNickname())
                .userIdx(user.getId())
                .profileImgUrl(AwsS3ImageUrlUtil.toUrl(user.getProfileImgKey()))
                .build();
    }
}
