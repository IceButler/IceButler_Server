package com.example.icebutler_server.user.dto.request;

import lombok.Getter;

@Getter
public class PatchProfileReq {
  private String nickname;
  private String profileImgKey;
}
