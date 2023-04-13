package com.example.icebutler_server.user.service;

import com.example.icebutler_server.user.dto.response.IsEnableRes;
import com.example.icebutler_server.user.dto.response.MyProfileRes;
import com.example.icebutler_server.global.resolver.IsLogin;
import com.example.icebutler_server.user.dto.request.PatchProfileReq;
import com.example.icebutler_server.user.dto.request.PostNicknameReq;
import com.example.icebutler_server.user.dto.request.PostUserReq;
import com.example.icebutler_server.user.dto.response.PostNickNameRes;
import com.example.icebutler_server.user.dto.response.PostUserRes;

public interface UserService {
  PostUserRes signUpOrLogin(PostUserReq postUserReq);

  void modifyProfile(@IsLogin Long userIdx, PatchProfileReq patchProfileReq);

  PostNickNameRes checkNickname(PostNicknameReq postNicknameReq);

<<<<<<< Updated upstream
  IsEnableRes deleteUser(Long userIdx);

  IsEnableRes logout(Long userIdx);
=======
  void deleteUser(Long userIdx);

  void logout(Long userIdx);
>>>>>>> Stashed changes

  //마이페이지 조회
  MyProfileRes myProfile(Long userIdx);
}
