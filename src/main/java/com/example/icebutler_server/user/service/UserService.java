package com.example.icebutler_server.user.service;

import com.example.icebutler_server.global.resolver.IsLogin;
import com.example.icebutler_server.user.dto.LoginUserReq;
import com.example.icebutler_server.user.dto.request.PatchProfileReq;
import com.example.icebutler_server.user.dto.request.PostNicknameReq;
import com.example.icebutler_server.user.dto.request.PostUserReq;
import com.example.icebutler_server.user.dto.response.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
  PostUserRes join(PostUserReq postUserReq);

  PostUserRes login(LoginUserReq loginUserReq);

  void modifyProfile(@IsLogin Long userId, PatchProfileReq patchProfileReq);

  PostNickNameRes checkNickname(PostNicknameReq postNicknameReq);

  void deleteUser(Long userId);

  void logout(Long userId);

  MyProfileRes checkProfile(Long userId);

  List<NickNameRes> searchNickname(String nickname);

  Page<MyNotificationRes> getUserNotification(Long userId, Pageable pageable);
}
