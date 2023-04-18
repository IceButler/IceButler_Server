package com.example.icebutler_server.user.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostNickNameRes {
  private String nickname;
  private boolean existence;

  @Builder
  public PostNickNameRes(String nickname, boolean existence) {
    this.nickname = nickname;
    this.existence = existence;
  }

  public static PostNickNameRes toDto(String nickname, Boolean existence) {
    return PostNickNameRes.builder()
            .nickname(nickname)
            .existence(existence)
            .build();
  }
}
