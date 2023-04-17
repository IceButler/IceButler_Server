package com.example.icebutler_server.global.feign.event;

import com.example.icebutler_server.global.feign.dto.AddUserReq;
import com.example.icebutler_server.user.entity.User;
import lombok.Getter;

@Getter
public class UserJoinEvent {
    private Long userIdx;
    private String nickname;
    private String profileImg;

    public static UserJoinEvent toEvent(User user){
        UserJoinEvent userJoinEvent = new UserJoinEvent();
        userJoinEvent.userIdx = user.getUserIdx();
        userJoinEvent.nickname = user.getNickname();
        userJoinEvent.profileImg = user.getProfileImgKey();
        return userJoinEvent;
    }

    public AddUserReq toDto() {
        return AddUserReq.builder()
                .userIdx(this.userIdx)
                .nickname(this.nickname)
                .profileImg(this.profileImg).build();
    }
}
