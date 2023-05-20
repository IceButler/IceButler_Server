package com.example.icebutler_server.admin.dto.response;

import com.example.icebutler_server.user.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserResponse {
    private Long userIdx;
    private String nickname;
    private String email;
    private String provider;
    private boolean isDenied;


    public static UserResponse toDto(User user)
    {
        UserResponse userResponse = new UserResponse();
        userResponse.userIdx = user.getUserIdx();
        userResponse.nickname = user.getNickname();
        userResponse.email = user.getEmail();
        userResponse.provider = user.getProvider().getName();
        userResponse.isDenied = user.getIsDenied();
        return userResponse;
    }
}
