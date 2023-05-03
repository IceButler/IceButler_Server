package com.example.icebutler_server.user.service;

import com.example.icebutler_server.global.resolver.IsLogin;
import com.example.icebutler_server.user.dto.LoginUserReq;
import com.example.icebutler_server.user.dto.request.PatchProfileReq;
import com.example.icebutler_server.user.dto.response.MyProfileRes;
import com.example.icebutler_server.user.dto.request.PostNicknameReq;
import com.example.icebutler_server.user.dto.request.PostUserReq;
import com.example.icebutler_server.user.dto.response.NickNameRes;
import com.example.icebutler_server.user.dto.response.PostNickNameRes;
import com.example.icebutler_server.user.dto.response.PostUserRes;

public interface UserService {
  PostUserRes join(PostUserReq postUserReq);

  PostUserRes login(LoginUserReq loginUserReq);

  void modifyProfile(@IsLogin Long userIdx, PatchProfileReq patchProfileReq);

  PostNickNameRes checkNickname(PostNicknameReq postNicknameReq);

  void deleteUser(Long userIdx);

  void logout(Long userIdx);
  //마이페이지 조회

  MyProfileRes checkProfile(Long userIdx);

  NickNameRes searchNickname(String nickname);
}
