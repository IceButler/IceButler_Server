package com.example.icebutler_server.user.dto.response;

import com.example.icebutler_server.user.entity.User;
import lombok.*;

@Data
@RequiredArgsConstructor
public class MyProfileRes {

    private Long userIdx;
    private String nickName;
    private String profileImgUrl;
    private String email;

    public static MyProfileRes toDto(User user) {
        MyProfileRes myProfileRes = new MyProfileRes();
        myProfileRes.userIdx = user.getUserIdx();
        myProfileRes.nickName = user.getNickname();
        myProfileRes.profileImgUrl = user.getProfileImage();
        myProfileRes.email=user.getEmail();
        return myProfileRes;
    }
}
