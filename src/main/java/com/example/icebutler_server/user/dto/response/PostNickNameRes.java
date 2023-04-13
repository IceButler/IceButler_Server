package com.example.icebutler_server.user.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostNickNameRes {
  private String nickName;
  private boolean existence;

  @Builder
  public PostNickNameRes(String nickName, boolean existence) {
    this.nickName = nickName;
    this.existence = existence;
  }
}
