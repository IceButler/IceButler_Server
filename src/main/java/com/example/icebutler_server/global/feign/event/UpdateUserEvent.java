package com.example.icebutler_server.global.feign.event;

import com.example.icebutler_server.global.feign.dto.UserReq;
import com.example.icebutler_server.user.entity.User;
import lombok.Getter;

@Getter
public class UpdateUserEvent {
    private Long userIdx;
    private String nickname;
    private String profileImgKey;
    private String email;

    public static UpdateUserEvent toEvent(User user){
        UpdateUserEvent userJoinEvent = new UpdateUserEvent();
        userJoinEvent.userIdx = user.getUserIdx();
        userJoinEvent.email = user.getEmail();
        userJoinEvent.nickname = user.getNickname();
        userJoinEvent.profileImgKey = user.getProfileImgKey();
        return userJoinEvent;
    }

    public UserReq toDto() {
        return UserReq.builder()
                .userIdx(this.userIdx)
                .nickname(this.nickname)
                .email(this.email)
                .profileImgKey(this.profileImgKey).build();
    }
}
