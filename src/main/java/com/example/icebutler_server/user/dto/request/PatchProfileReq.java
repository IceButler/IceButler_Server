package com.example.icebutler_server.user.dto.request;

import lombok.Getter;

@Getter
public class PatchProfileReq {
  private String nickName;
  private String profileImage;
}