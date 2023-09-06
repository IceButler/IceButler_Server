package com.example.icebutler_server.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PatchProfileReq {
  private String nickname;
  private String profileImgKey;
}
