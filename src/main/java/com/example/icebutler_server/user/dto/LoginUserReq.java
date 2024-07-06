package com.example.icebutler_server.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(name = "LoginUserReq", description = "유저 로그인 요청 정보")
public class LoginUserReq {
  @Schema(name = "email", description = "이메일")
  private String email;
  @Schema(name = "provider", description = "프로바이더")
  private String provider;
  @Schema(name = "fcmToken", description = "유저 FCM 토큰")
  private String fcmToken;
}
