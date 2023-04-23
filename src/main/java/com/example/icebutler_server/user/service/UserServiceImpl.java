package com.example.icebutler_server.user.service;


import com.example.icebutler_server.global.resolver.IsLogin;
import com.example.icebutler_server.global.util.RedisTemplateService;
import com.example.icebutler_server.global.util.TokenUtils;
import com.example.icebutler_server.user.dto.LoginUserReq;
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
import org.springframework.util.StringUtils;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserAssembler userAssembler;
  private final TokenUtils tokenUtils;
   private final RedisTemplateService redisTemplateService;

  // 소셜로그인
  @Transactional
  public PostUserRes join(PostUserReq postUserReq) {
    User user = checkUserInfo(postUserReq.getEmail(), postUserReq.getProvider());

    if (user == null) user = saveUser(postUserReq);
    if (user.getIsEnable().equals(false)) throw new AlreadyWithdrawUserException();

    user.login();

    return PostUserRes.toDto(tokenUtils.createToken(user));
  }

  @Transactional
  public PostUserRes login(LoginUserReq loginUserReq) {
    User user = checkUserInfo(loginUserReq.getEmail(), loginUserReq.getProvider());

    if (user != null) {
      user.login();
      return PostUserRes.toDto(tokenUtils.createToken(user));
    }
    return null;
  }

  public User checkUserInfo(String email, String provider) {
    if(Provider.getProviderByName(provider) == null) throw new ProviderMissingValueException();
    if(!StringUtils.hasText(email)) throw new UserEmailMissingValueException();

    return userRepository.findByEmailAndProvider(email, Provider.getProviderByName(provider));
  }

  @Transactional
  public User saveUser(PostUserReq postUserReq) {
    return userRepository.save(User.builder()
            .provider(Provider.getProviderByName(postUserReq.getProvider()))
            .email(postUserReq.getEmail())
            .nickname(postUserReq.getNickname())
            .profileImgKey(postUserReq.getProfileImgKey())
            .build());
  }

  // 프로필 설정
  @Transactional
  public void modifyProfile(@IsLogin Long userIdx, PatchProfileReq patchProfileReq) {
    User user = userRepository.findByUserIdxAndIsEnable(userIdx, true).orElseThrow(UserNotFoundException::new);

    if (!StringUtils.hasText(patchProfileReq.getNickname())) throw new InvalidUserNickNameException();
    if (!StringUtils.hasText(patchProfileReq.getProfileImgKey())) throw new InvalidUserProfileImgKeyException();

    user.modifyProfile(patchProfileReq.getNickname(), patchProfileReq.getProfileImgKey());
  }

  // 닉네임 중복 확인
  public PostNickNameRes checkNickname(PostNicknameReq postNicknameReq) {
    if (!userAssembler.isValidNickname(postNicknameReq.getNickname())) throw new InvalidUserNickNameException();
    Boolean existence = userRepository.existsByNickname(postNicknameReq.getNickname());

    return PostNickNameRes.toDto(postNicknameReq.getNickname(), existence);
  }

  //유저 탈퇴
  @Override
  @Transactional
  public void deleteUser(Long userIdx) {
    User user = userRepository.findByUserIdxAndIsEnable(userIdx, true).orElseThrow(UserNotFoundException::new);
        redisTemplateService.deleteUserRefreshToken(userIdx);
    user.setIsEnable(false);
  }

  //유저 로그아웃
  @Override
  @Transactional
  public void logout(Long userIdx) {
    User user = userRepository.findByUserIdxAndIsEnable(userIdx, true).orElseThrow(UserNotFoundException::new);
        redisTemplateService.deleteUserRefreshToken(userIdx);
    user.logout();
  }

  //마이페이지 조회
  @Override
  public MyProfileRes myProfile(Long userIdx) {
    User user = userRepository.findByUserIdxAndIsEnable(userIdx, true).orElseThrow(UserNotFoundException::new);

    return MyProfileRes.toDto(user);

  }

}
