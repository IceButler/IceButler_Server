package com.example.icebutler_server.fridge.dto.response;

import com.example.icebutler_server.global.util.AwsS3ImageUrlUtil;
import com.example.icebutler_server.user.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FridgeUsersRes {
    private Long userIdx;
    private String nickName;
    private String profileImageUrl;

    public static FridgeUsersRes toDto(User user){
        FridgeUsersRes fridgeUsersRes=new FridgeUsersRes();
        fridgeUsersRes.nickName=user.getNickname();
        fridgeUsersRes.userIdx=user.getId();
        fridgeUsersRes.profileImageUrl=AwsS3ImageUrlUtil.toUrl(user.getProfileImgKey());
        return fridgeUsersRes;
    }
}
