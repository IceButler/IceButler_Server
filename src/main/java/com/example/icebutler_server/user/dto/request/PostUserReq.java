package com.example.icebutler_server.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(name = "PostUserReq", description = "유저 회원가입 요청 정보")
@Getter
public class PostUserReq {
  @Schema(description = "프로바이더", allowableValues = {"카카오", "애플"}, example = "카카오")
  private String provider;
  @Schema(description = "이메일", example = "abd@email.com")
  private String email;
  @Schema(description = "닉네임")
  private String nickname;
  @Schema(description = "프로필 이미지 키")
  private String profileImgKey;
  @Schema(description = "FCM 토큰")
  private String fcmToken;
}
