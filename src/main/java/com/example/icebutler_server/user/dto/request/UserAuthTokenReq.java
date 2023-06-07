package com.example.icebutler_server.user.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Getter
public class UserAuthTokenReq {

    private Long userIdx;

    private String nickname;

    @Builder
    public UserAuthTokenReq(Long userIdx, String nickname) {
        this.userIdx = userIdx;
        this.nickname = nickname;
    }

}
