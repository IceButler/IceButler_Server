package com.example.icebutler_server.user.service;

import com.example.icebutler_server.user.dto.MyProfileRes;
import com.example.icebutler_server.global.resolver.IsLogin;
import com.example.icebutler_server.user.dto.PatchProfileReq;
import com.example.icebutler_server.user.dto.PostNicknameReq;
import com.example.icebutler_server.user.dto.request.PostUserReq;
import com.example.icebutler_server.user.dto.response.PostUserRes;

public interface UserService {
  PostUserRes signUpOrLogin(PostUserReq postUserReq);
  void modifyProfile(@IsLogin Long userIdx, PatchProfileReq patchProfileReq);
  void checkNickname(PostNicknameReq postNicknameReq);
      Boolean deleteUser(Long userIdx);

    Boolean logout(Long userIdx);

    //마이페이지 조회
    MyProfileRes myProfile(Long userIdx);
}
