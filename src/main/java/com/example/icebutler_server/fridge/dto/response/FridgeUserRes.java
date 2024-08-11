package com.example.icebutler_server.fridge.dto.response;

import com.example.icebutler_server.fridge.entity.FridgeUser;
import com.example.icebutler_server.global.entity.FridgeRole;
import com.example.icebutler_server.global.util.AwsS3ImageUrlUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(name = "냉장고 유저 정보", description = "FridgeUserRe")
public class FridgeUserRes {
    @Schema(description = "유저 ID", example = "1")
    private Long userId;
    @Schema(description = "유저 닉네임", example = "나야나")
    private String nickname;
    @Schema(description = "냉장고 내 유저 역할", example = "OWNER/MEMBER")
    private FridgeRole role;
    @Schema(description = "유저 프로필 이미지 URL", example = "https://~/asdfeg.jpg")
    private String profileImgUrl;

    public static FridgeUserRes toDto(FridgeUser fridgeUser) {
        return FridgeUserRes.builder()
                .userId(fridgeUser.getUser().getId())
                .nickname(fridgeUser.getUser().getNickname())
                .role(fridgeUser.getRole())
                .profileImgUrl(AwsS3ImageUrlUtil.toUrl(fridgeUser.getUser().getProfileImgKey()))
                .build();
    }
}
