package com.example.icebutler_server.user.dto.assembler;

import com.example.icebutler_server.user.dto.request.PostUserReq;
import com.example.icebutler_server.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class UserAssembler {

  public User signUpOrLogin(User user, PostUserReq postUserReq) {
    user.login(postUserReq.getFcmToken());
    return user;
  }

//  2자 이상 8자 이하, 영어 또는 숫자 또는 한글로 구성
//  특이사항 : 한글 초성 및 모음은 허가하지 않는다.
  public Boolean isValidNickname(String nickname) {
    boolean err = false;
    String regex = "^(?=.*[a-z0-9가-힣])[a-z0-9가-힣]{2,8}$";
    Pattern p = Pattern.compile(regex);
    Matcher m = p.matcher(nickname);
    if(m.matches()) {
      err = true;
    }
    return err;
  }
}
