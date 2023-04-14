package com.example.icebutler_server.user.service;


import com.example.icebutler_server.global.resolver.IsLogin;
import com.example.icebutler_server.user.dto.assembler.UserAssembler;
import com.example.icebutler_server.user.dto.request.PatchProfileReq;
import com.example.icebutler_server.user.dto.request.PostNicknameReq;
import com.example.icebutler_server.user.dto.request.PostUserReq;
import com.example.icebutler_server.user.dto.response.PostNickNameRes;
import com.example.icebutler_server.user.dto.response.PostUserRes;
import com.example.icebutler_server.user.entity.Provider;
import com.example.icebutler_server.user.entity.User;
import com.example.icebutler_server.user.exception.*;
//import com.example.icebutler_server.global.util.RedisTemplateService;
import com.example.icebutler_server.user.dto.response.MyProfileRes;
import com.example.icebutler_server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final AuthService authService;
  private final UserAssembler userAssembler;
  // private final RedisTemplateService redisTemplateService;

  // 소셜로그인
  @Transactional
  public PostUserRes signUpOrLogin(PostUserReq postUserReq) {
    if (Provider.getProviderByName(postUserReq.getProvider()) == null) throw new ProviderMissingValueException();

    User user = userRepository.findByEmailAndProvider(postUserReq.getEmail(), Provider.getProviderByName(postUserReq.getProvider()));
    return authService.createToken(userRepository.save(userAssembler.signUpOrLogin(user, postUserReq)));
  }

//  // 프로필 설정
//  @Transactional
//  public void modifyProfile(@IsLogin Long userIdx, PatchProfileReq patchProfileReq) {
//    User user = userRepository.findByUserIdxAndIsEnable(userIdx, true).orElseThrow(UserNotFoundException::new);
//
//    if(!userAssembler.isValidNickName(patchProfileReq.getNickName())) throw new InvalidUserNickNameException();
//    if (patchProfileReq.getNickName() != null) user.modifyNickname(patchProfileReq.getNickName());
//    if (patchProfileReq.getProfileImgUrl() != null) user.modifyProfileImg(patchProfileReq.getProfileImgUrl());
//  }

  // 닉네임 중복 확인
  public PostNickNameRes checkNickname(PostNicknameReq postNicknameReq) {
    if(!userAssembler.isValidNickName(postNicknameReq.getNickName())) throw new InvalidUserNickNameException();
    Boolean existence = userRepository.existsByNickname(postNicknameReq.getNickName());

    return PostNickNameRes.builder().nickName(postNicknameReq.getNickName()).existence(existence).build();
  }

  //유저 탈퇴
  @Override
  @Transactional
  public void deleteUser(Long userIdx) {
    User user = userRepository.findByUserIdxAndIsEnable(userIdx,true).orElseThrow(UserNotFoundException::new);
//        redisTemplateService.deleteUserRefreshToken(userIdx);
    user.setIsEnable(false);
  }

  //유저 로그아웃
  @Override
  public void logout(Long userIdx) {
    User user=userRepository.findByUserIdxAndIsEnable(userIdx,true).orElseThrow(UserNotFoundException::new);
//        redisTemplateService.deleteUserRefreshToken(userIdx)
    user.logout();
  }

  //마이페이지 조회
  @Override
  public MyProfileRes myProfile(Long userIdx) {
    User user = userRepository.findByUserIdxAndIsEnable(userIdx,true).orElseThrow(UserNotFoundException::new);

    return MyProfileRes.builder()
            .userIdx(user.getUserIdx())
            .nickName(user.getNickname())
            .profileImage(user.getProfileImage())
            .build();
  }

}
