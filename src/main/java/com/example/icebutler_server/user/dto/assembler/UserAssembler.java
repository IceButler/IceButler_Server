package com.example.icebutler_server.user.dto.assembler;

import com.example.icebutler_server.user.dto.request.PostUserReq;
import com.example.icebutler_server.user.entity.Provider;
import com.example.icebutler_server.user.entity.User;
import com.example.icebutler_server.user.exception.AlreadyWithdrawUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAssembler {

  public User signUpOrLogin(User user, PostUserReq postUserReq) {
    if (user == null) {
      user = User.builder()
              .provider(Provider.getProviderByName(postUserReq.getProvider()))
              .email(postUserReq.getEmail())
              .build();
    }
    if (user.getIsEnable().equals(false)) throw new AlreadyWithdrawUserException();

    user.login();
    return user;
  }
}
