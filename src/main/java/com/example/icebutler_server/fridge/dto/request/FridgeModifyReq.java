package com.example.icebutler_server.fridge.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "FridgeModifyReq", description = "냉장고 수정 요청 정보")
public class FridgeModifyReq {
  @Schema(name = "fridgeName", description = "냉장고 이름")
  private String fridgeName;
  @Schema(name = "fridgeComment", description = "냉장고 설명")
  private String fridgeComment;
  @Schema(name = "members", description = "냉장고 멤버 ID")
  private List<FridgeModifyMembersReq> members;
  @Schema(name = "newOwnerId", description = "냉장고 주인 ID")
  private Long newOwnerId;
}
