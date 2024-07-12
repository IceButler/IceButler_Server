package com.example.icebutler_server.fridge.dto.response;

import com.example.icebutler_server.global.util.AwsS3ImageUrlUtil;
import com.example.icebutler_server.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(name = "FridgeUsersRes", description = "냉장고 멤버 정보")
public class FridgeUsersRes {
    @Schema(name = "userId", description = "유저 ID")
    private Long userId;
    @Schema(name = "nickName", description = "유저 닉네임")
    private String nickName;
    @Schema(name = "profileImageUrl", description = "유저 프로필 이미지 URL")
    private String profileImageUrl;

    public static FridgeUsersRes toDto(User user){
        FridgeUsersRes fridgeUsersRes=new FridgeUsersRes();
        fridgeUsersRes.nickName=user.getNickname();
        fridgeUsersRes.userId =user.getId();
        fridgeUsersRes.profileImageUrl=AwsS3ImageUrlUtil.toUrl(user.getProfileImgKey());
        return fridgeUsersRes;
    }
}
