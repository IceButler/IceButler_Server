package com.example.icebutler_server.fridge.dto.request;

import com.example.icebutler_server.fridge.entity.fridge.FridgeUser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "FridgeRegisterReq", description = "냉장고 등록 요청 정보")
public class FridgeRegisterReq {
  @Schema(name = "fridgeName", description = "냉장고 이름")
  private String fridgeName;
  @Schema(name = "fridgeComment", description = "냉장고 설명")
  private String fridgeComment;
  @Schema(name = "FridgeRegisterMembersReq", description = "냉장고 멤버 ID")
  private List<FridgeRegisterMembersReq> members;
}
