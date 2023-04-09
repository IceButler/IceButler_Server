package com.example.icebutler_server.user.service;

import com.example.icebutler_server.global.dto.response.ResponseCustom;
import com.example.icebutler_server.user.dto.UserSearchRes;

public interface UserService {
//    Boolean deleteUser(Long userIdx);
//
//    Boolean logout(Long userIdx);
//
//    GetProfileRes getProfile(Long userIdx);

    ResponseCustom<UserSearchRes> searchUsers(String nickName );

//    ResponseCustom<FridgeUserRes> searchFridgeUsers(Long fridgeUserIdx);
}