package com.example.icebutler_server.user.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatchProfileReq {
  private String nickname;
  private String profileImgKey;
}
