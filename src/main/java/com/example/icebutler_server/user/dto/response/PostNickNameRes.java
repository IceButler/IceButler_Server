package com.example.icebutler_server.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(name = "PostNickNameRes", description = "닉네임 유저 중복 조회 정보")
public class PostNickNameRes {
  @Schema(name = "nickname", description = "닉네임")
  private String nickname;
  @Schema(name = "existence", description = "닉네임 존재 유무")
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
