package com.example.icebutler_server.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PatchProfileReq {
  private String nickname;
  private String profileImgKey;
}
