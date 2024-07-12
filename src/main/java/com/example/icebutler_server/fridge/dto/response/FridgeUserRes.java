package com.example.icebutler_server.fridge.dto.response;

import com.example.icebutler_server.global.entity.FridgeRole;
import com.example.icebutler_server.global.util.AwsS3ImageUrlUtil;
import com.example.icebutler_server.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "FridgeUserRes", description = "냉장고 유저 정보")
public class FridgeUserRes {
    @Schema(name = "userId", description = "유저 ID")
    private Long userId;
    @Schema(name = "nickname", description = "유저 닉네임")
    private String nickname;
    @Schema(name = "role", description = "냉장고 내 유저 역할")
    private FridgeRole role;
    @Schema(name = "profileImgUrl", description = "유저 프로필 이미지 URL")
    private String profileImgUrl;

    public static FridgeUserRes toDto(User user, FridgeRole role) {
        FridgeUserRes fridgeUserRes = new FridgeUserRes();
        fridgeUserRes.userId = user.getId();
        fridgeUserRes.nickname = user.getNickname();
        fridgeUserRes.role = role;
        fridgeUserRes.profileImgUrl = AwsS3ImageUrlUtil.toUrl(user.getProfileImgKey());
        return fridgeUserRes;
    }

}
