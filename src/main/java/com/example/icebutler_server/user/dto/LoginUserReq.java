package com.example.icebutler_server.user.dto;

import lombok.Getter;

@Getter
public class LoginUserReq {
  private String email;
  private String provider;
  private String fcmToken;
}
