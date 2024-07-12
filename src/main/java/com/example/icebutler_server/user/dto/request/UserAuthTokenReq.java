package com.example.icebutler_server.user.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Getter
public class UserAuthTokenReq {

    private Long userId;

    private String nickname;

    @Builder
    public UserAuthTokenReq(Long userId, String nickname) {
        this.userId = userId;
        this.nickname = nickname;
    }

}
