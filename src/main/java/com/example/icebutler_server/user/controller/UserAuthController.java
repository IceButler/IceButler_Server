package com.example.icebutler_server.user.controller;

import com.example.icebutler_server.global.dto.response.ResponseCustom;
import com.example.icebutler_server.global.resolver.Auth;
import com.example.icebutler_server.global.resolver.IsLogin;
import com.example.icebutler_server.global.resolver.LoginStatus;
import com.example.icebutler_server.global.util.TokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class UserAuthController {

  private final TokenUtils tokenUtils;

  @Auth
  @GetMapping("/renew")
  public ResponseCustom<String> accessToken(@IsLogin LoginStatus loginStatus) {
    return ResponseCustom.OK(tokenUtils.accessExpiration(loginStatus.getUserIdx()));
  }


}
