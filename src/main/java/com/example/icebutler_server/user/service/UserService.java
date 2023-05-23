package com.example.icebutler_server.user.service;

import com.example.icebutler_server.global.resolver.IsLogin;
import com.example.icebutler_server.user.dto.LoginUserReq;
import com.example.icebutler_server.user.dto.request.PatchProfileReq;
import com.example.icebutler_server.user.dto.response.*;
import com.example.icebutler_server.user.dto.request.PostNicknameReq;
import com.example.icebutler_server.user.dto.request.PostUserReq;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface UserService {
  PostUserRes join(PostUserReq postUserReq);

  PostUserRes login(LoginUserReq loginUserReq);

  void modifyProfile(@IsLogin Long userIdx, PatchProfileReq patchProfileReq);

  PostNickNameRes checkNickname(PostNicknameReq postNicknameReq);

  void deleteUser(Long userIdx);

  void logout(Long userIdx);
  //마이페이지 조회

  MyProfileRes checkProfile(Long userIdx);

  List<NickNameRes> searchNickname(String nickname);

  Page<MyNotificationRes> getUserNotification(Long userIdx, Pageable pageable);
}
