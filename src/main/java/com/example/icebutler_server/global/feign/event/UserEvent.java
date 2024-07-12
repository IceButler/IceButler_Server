package com.example.icebutler_server.global.feign.event;

import com.example.icebutler_server.global.feign.dto.UserReq;
import com.example.icebutler_server.user.entity.User;
import lombok.Getter;

@Getter
public class UserEvent {
    private Long userId;
    private String nickname;
    private String profileImgKey;
    private String email;

    public static UserEvent toEvent(User user){
        UserEvent userJoinEvent = new UserEvent();
        userJoinEvent.userId = user.getId();
        userJoinEvent.email = user.getEmail();
        userJoinEvent.nickname = user.getNickname();
        userJoinEvent.profileImgKey = user.getProfileImgKey();
        return userJoinEvent;
    }

    public UserReq toDto() {
        return UserReq.builder()
                .userId(this.userId)
                .nickname(this.nickname)
                .email(this.email)
                .profileImgKey(this.profileImgKey).build();
    }
}
