package com.example.icebutler_server.user.dto.response;

import com.example.icebutler_server.user.entity.User;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NickNameRes {
    private  String nickname;
    private  Long userIdx;


    public static NickNameRes toDto(User user){
        return NickNameRes.builder()
                .nickname(user.getNickname())
                .userIdx(user.getUserIdx())
                .build();
    }
}
