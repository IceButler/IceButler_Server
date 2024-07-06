package com.example.icebutler_server.fridge.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "FridgeRegisterMembersReq", description = "냉장고 등록 멤버 ID")
public class FridgeRegisterMembersReq {
  @Schema(name = "userIdx", description = "유저 ID")
  private Long userIdx;
}
