package com.example.icebutler_server.fridge.dto.response;

import com.example.icebutler_server.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FridgeUserRes {
    private String nickname;

    public static FridgeUserRes toDto(User user) {
        FridgeUserRes fridgeUserRes = new FridgeUserRes();
        fridgeUserRes.nickname = user.getNickname();
        return fridgeUserRes;
    }
}
