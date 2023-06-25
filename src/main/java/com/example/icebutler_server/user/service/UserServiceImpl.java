package com.example.icebutler_server.user.service;

import com.example.icebutler_server.alarm.repository.PushNotificationRepository;
import com.example.icebutler_server.fridge.entity.fridge.Fridge;
import com.example.icebutler_server.fridge.entity.fridge.FridgeUser;
import com.example.icebutler_server.fridge.repository.fridge.FridgeRepository;
import com.example.icebutler_server.fridge.repository.fridge.FridgeUserRepository;
import com.example.icebutler_server.global.entity.FridgeRole;
import com.example.icebutler_server.global.feign.publisher.RecipeServerEventPublisherImpl;
import com.example.icebutler_server.global.resolver.IsLogin;
import com.example.icebutler_server.global.util.redis.RedisTemplateService;
import com.example.icebutler_server.global.util.redis.RedisUtils;
import com.example.icebutler_server.global.util.TokenUtils;
import com.example.icebutler_server.user.dto.LoginUserReq;
import com.example.icebutler_server.user.dto.assembler.UserAssembler;
import com.example.icebutler_server.user.dto.request.PatchProfileReq;
import com.example.icebutler_server.user.dto.request.PostNicknameReq;
import com.example.icebutler_server.user.dto.request.PostUserReq;
import com.example.icebutler_server.user.dto.response.*;
import com.example.icebutler_server.user.entity.Provider;
import com.example.icebutler_server.user.entity.User;
import com.example.icebutler_server.user.exception.*;
import com.example.icebutler_server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final FridgeUserRepository fridgeUserRepository;
  private final FridgeRepository fridgeRepository;
  private final UserAssembler userAssembler;
  private final TokenUtils tokenUtils;
  private final RedisUtils redisUtils;

  private final RecipeServerEventPublisherImpl recipeServerEventPublisher;
  private final RedisTemplateService redisTemplateService;
  private final PushNotificationRepository pushNotificationRepository;
  // 소셜로그인
  @Transactional
  public PostUserRes join(PostUserReq postUserReq) {
    User user = checkUserInfo(postUserReq.getEmail(), postUserReq.getProvider());
    if (user == null) user = saveUser(postUserReq);
    // 정지된 회원은 재가입 불가
    if (user.getIsDenied().equals(true)) throw new AccessDeniedUserException();
    // 자진 탈퇴 회원은 재가입 처리
    if (user.getIsEnable().equals(false)) user=saveUser(postUserReq); // 새로운 행 추가

    user.login(postUserReq.getFcmToken());
    this.recipeServerEventPublisher.addUser(user);
    return PostUserRes.toDto(tokenUtils.createToken(user));
  }

  @Transactional
  public PostUserRes login(LoginUserReq loginUserReq) {
    User user = checkUserInfo(loginUserReq.getEmail(), loginUserReq.getProvider());

    if (user != null) {
      if (user.getIsEnable().equals(false)) throw new AlreadyWithdrawUserException();
      user.login(loginUserReq.getFcmToken());
      return PostUserRes.toDto(tokenUtils.createToken(user));
    }
    return null;
  }


  public User checkUserInfo(String email, String provider) {
    if (Provider.getProviderByName(provider) == null) throw new ProviderMissingValueException();
    if (!StringUtils.hasText(email)) throw new UserEmailMissingValueException();

    return userRepository.findByEmailAndProvider(email, Provider.getProviderByName(provider));
  }

  @Transactional
  public User saveUser(PostUserReq postUserReq) {
    return userRepository.save(User.builder()
            .provider(Provider.getProviderByName(postUserReq.getProvider()))
            .email(postUserReq.getEmail())
            .nickname(postUserReq.getNickname())
            .profileImgKey(postUserReq.getProfileImgKey())
            .fcmToken(postUserReq.getFcmToken())
            .build());
  }

  // 프로필 설정
  @Transactional
  public boolean modifyProfile(@IsLogin Long userIdx, PatchProfileReq patchProfileReq) {
    User user = userRepository.findByUserIdxAndIsEnable(userIdx, true).orElseThrow(UserNotFoundException::new);

    if (StringUtils.hasText(patchProfileReq.getNickname())) user.modifyProfileNickName(patchProfileReq.getNickname());
    if (StringUtils.hasText(patchProfileReq.getProfileImgKey())) user.modifyProfileImgKey(patchProfileReq.getProfileImgKey());

//    user.modifyProfileNickName(patchProfileReq.getNickname(), patchProfileReq.getProfileImgKey());
//    recipeServerEventPublisher.changeUserProfile(user);
    return true;
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
    List<FridgeUser> fridgeOwners = fridgeUserRepository.findByUserAndRoleAndIsEnable(user, FridgeRole.OWNER, true);
    for (FridgeUser fridgeOwner : fridgeOwners) {
      Fridge fridge = fridgeOwner.getFridge();
      List<FridgeUser> fridgeMembers = fridgeUserRepository.findByFridgeAndRoleAndIsEnable(fridge, FridgeRole.MEMBER, true);
      if (fridgeMembers.size() > 0) {
        throw new CannotDeleteFridgeException();
      }
      fridgeRepository.delete(fridge);
    }
    user.deleteUser();
    redisTemplateService.deleteUserRefreshToken(userIdx.toString());
//    user.setIsEnable(false);
    recipeServerEventPublisher.deleteUser(user);
  }

  //유저 로그아웃
  @Override
  @Transactional
  public void logout(Long userIdx) {
    User user = userRepository.findByUserIdxAndIsEnable(userIdx, true).orElseThrow(UserNotFoundException::new);
    redisTemplateService.deleteUserRefreshToken(userIdx.toString());
    user.logout();
  }

  //마이페이지 조회
  @Override
  public MyProfileRes checkProfile(Long userIdx) {
    User user = userRepository.findByUserIdxAndIsEnable(userIdx, true).orElseThrow(UserNotFoundException::new);

    return MyProfileRes.toDto(user);

  }

  @Override
  public List<NickNameRes> searchNickname(String nickname) {
    return userRepository.findByNicknameContains(nickname)
            .stream().map(NickNameRes::toDto).collect(Collectors.toList());
//    return NickNameRes.toDto(user.getNickname());
  }

  @Override
  public Page<MyNotificationRes> getUserNotification(Long userIdx, Pageable pageable) {
    User user = userRepository.findByUserIdxAndIsEnable(userIdx, true).orElseThrow(UserNotFoundException::new);
    return this.userAssembler.toUserNotificationList(this.pushNotificationRepository.findByUserOrderByCreatedAtDesc(user, pageable));
  }

}
