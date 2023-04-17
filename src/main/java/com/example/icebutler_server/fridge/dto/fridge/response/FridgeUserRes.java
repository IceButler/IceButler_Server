package com.example.icebutler_server.fridge.dto.fridge.response;

import com.example.icebutler_server.global.entity.FridgeRole;
import com.example.icebutler_server.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FridgeUserRes {
    private String nickname;
    private FridgeRole role;
    private String profileImage;

    public static FridgeUserRes toDto(User user, FridgeRole role) {
        FridgeUserRes fridgeUserRes = new FridgeUserRes();
        fridgeUserRes.nickname = user.getNickname();
        fridgeUserRes.role = role;
        fridgeUserRes.profileImage = user.getProfileImgKey();
        return fridgeUserRes;
    }

}
