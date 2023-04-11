package com.example.icebutler_server.user.service;

import com.example.icebutler_server.user.dto.MyProfileRes;

public interface UserService {
    Boolean deleteUser(Long userIdx);

    Boolean logout(Long userIdx);

    //마이페이지 조회
    MyProfileRes myProfile(Long userIdx);
}
