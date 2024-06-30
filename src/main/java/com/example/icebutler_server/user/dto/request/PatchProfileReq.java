package com.example.icebutler_server.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(name = "PatchProfileReq", description = "유저 프로필 수정 요청 정보")
public class PatchProfileReq {
  @Schema(name = "nickname", description = "닉네임")
  private String nickname;
  @Schema(name = "profileImgKey", description = "닉네임 프로필 이미지 KEY")
  private String profileImgKey;
}
