package com.example.icebutler_server.user.dto.request;

import lombok.Getter;

@Getter
public class PostUserReq {
  private String provider;
  private String email;
  private String nickname;
  private String profileImgKey;
  private String fcmToken;
}
