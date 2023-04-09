package com.example.icebutler_server.user.dto;

import com.example.icebutler_server.user.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserSearchRes {
    private Long userId;
    private String userNickName;
    private boolean isUser;




    public static UserSearchRes toDto(User user) {
        UserSearchRes userSearchRes=new UserSearchRes();
        userSearchRes.userId=user.getUserIdx();
        userSearchRes.userNickName=user.getNickname();
        userSearchRes.isUser=user.getIsEnable();
        return userSearchRes;
    }
}