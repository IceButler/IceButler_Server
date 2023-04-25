package com.example.icebutler_server.global.feign.event;

import com.example.icebutler_server.global.feign.dto.UserReq;
import com.example.icebutler_server.user.entity.User;
import lombok.Getter;

@Getter
public class UserEvent {
    private Long userIdx;
    private String nickname;
    private String profileImgKey;

    public static UserEvent toEvent(User user){
        UserEvent userJoinEvent = new UserEvent();
        userJoinEvent.userIdx = user.getUserIdx();
        userJoinEvent.nickname = user.getNickname();
        userJoinEvent.profileImgKey = user.getProfileImgKey();
        return userJoinEvent;
    }

    public UserReq toDto() {
        return UserReq.builder()
                .userIdx(this.userIdx)
                .nickname(this.nickname)
                .profileImgKey(this.profileImgKey).build();
    }
}
