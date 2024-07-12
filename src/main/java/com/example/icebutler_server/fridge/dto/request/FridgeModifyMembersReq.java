package com.example.icebutler_server.fridge.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "FridgeModifyMembersReq", description = "냉장고 수정 멤버 요청 정보")
public class FridgeModifyMembersReq {
  @Schema(name = "userId", description = "유저 ID")
  private Long userId;
}
