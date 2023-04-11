package com.example.icebutler_server.user.service;

import com.example.icebutler_server.global.resolver.IsLogin;
import com.example.icebutler_server.user.dto.PatchProfileReq;
import com.example.icebutler_server.user.dto.PostNicknameReq;
import com.example.icebutler_server.user.dto.request.PostUserReq;
import com.example.icebutler_server.user.dto.response.PostUserRes;
import com.example.icebutler_server.user.entity.Provider;
import com.example.icebutler_server.user.entity.User;
import com.example.icebutler_server.user.exception.AlreadyExistNickNameException;
import com.example.icebutler_server.user.exception.AlreadyWithdrawUserException;
import com.example.icebutler_server.user.exception.ProviderMissingValueException;
import com.example.icebutler_server.user.exception.UserNotFoundException;
import com.example.icebutler_server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final AuthService authService;

  @Transactional
  public PostUserRes signUpOrLogin(PostUserReq postUserReq) {
    if (Provider.getProviderByName(postUserReq.getProvider()) == null) throw new ProviderMissingValueException();

    User user = userRepository.findByEmailAndProvider(postUserReq.getEmail(), Provider.getProviderByName(postUserReq.getProvider()));
    if (user == null) user = signUp(postUserReq);
    if (user.getIsEnable().equals(false)) throw new AlreadyWithdrawUserException();

    user.login();
    userRepository.save(user);

    return authService.createToken(user);
  }

  @Transactional
  public User signUp(PostUserReq postUserReq) {
    User user = User.builder()
            .provider(Provider.getProviderByName(postUserReq.getProvider()))
            .email(postUserReq.getEmail())
            .build();
    return userRepository.save(user);
  }

  @Transactional
  public void modifyProfile(@IsLogin Long userIdx, PatchProfileReq patchProfileReq) {
    User user = userRepository.findByUserIdxAndIsEnable(userIdx, true).orElseThrow(UserNotFoundException::new);
    if (patchProfileReq.getNickName() != null) user.modifyNickname(patchProfileReq.getNickName());
    if (patchProfileReq.getProfileImage() != null) user.modifyProfileImg(patchProfileReq.getProfileImage());
  }

  // 닉네임 중복 확인
  public void checkNickname(PostNicknameReq postNicknameReq) {
    boolean existence = userRepository.existsByNickname(postNicknameReq.getNickName());
    if (existence) throw new AlreadyExistNickNameException();
  }
}
