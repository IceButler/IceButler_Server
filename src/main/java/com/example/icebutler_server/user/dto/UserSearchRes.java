package com.example.icebutler_server.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserSearchRes {
    private Long userId;
    private String userNickName;
    private String imageKey;
    private boolean isUser;



    @Builder
    public UserSearchRes(Long userId, String userNickName, boolean isUser) {
        this.userId = userId;
        this.userNickName = userNickName;
        this.isUser = isUser;
    }
}