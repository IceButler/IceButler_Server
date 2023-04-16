package com.example.icebutler_server.user.dto.response;

import com.example.icebutler_server.user.entity.User;
import lombok.*;

@Data
@RequiredArgsConstructor
public class MyProfileRes {

    private Long userIdx;
    private String nickName;
    private String profileImage;
    private String email;

    public MyProfileRes(User user) {
        this.userIdx = user.getUserIdx();
        this.nickName = user.getNickname();
        this.profileImage = user.getProfileImage();
        this.email=user.getEmail();
    }
}
