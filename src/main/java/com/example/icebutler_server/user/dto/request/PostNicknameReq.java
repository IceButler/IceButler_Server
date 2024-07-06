package com.example.icebutler_server.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(name = "PostNicknameReq", description = "닉네임 유저 중복 조회 요청 정보")
public class PostNicknameReq {
  @Schema(name = "nickname", description = "닉네임")
  private String nickname;
}
