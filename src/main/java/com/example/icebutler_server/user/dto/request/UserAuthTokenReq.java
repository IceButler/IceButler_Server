package com.example.icebutler_server.user.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Getter
public class UserAuthTokenReq {

//    @NotNull(message = "유저 ID를 입력해주세요")
//    @NotBlank(message = "유저 ID를 입력해주세요")
    private Long userIdx;

//    @NotNull(message = "유저 닉네임을 입력해주세요")
//    @NotBlank(message = "유저 닉네임을 입력해주세요")
    private String nickname;

    @Builder
    public UserAuthTokenReq(Long userIdx, String nickname) {
        this.userIdx = userIdx;
        this.nickname = nickname;
    }

}
