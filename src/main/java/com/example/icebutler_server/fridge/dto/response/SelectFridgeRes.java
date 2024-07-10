package com.example.icebutler_server.fridge.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "SelectFridgeRes", description = "냉장고 정보")
public class SelectFridgeRes {
  @Schema(name = "fridgeName", description = "냉장고 이름")
  private String fridgeName;
  @Schema(name = "fridgeId", description = "냉장고 ID")
  private Long fridgeId;

  public static SelectFridgeRes toDto(String fridgeName, Long fridgeId) {
    SelectFridgeRes selectFridgeRes = new SelectFridgeRes();
    selectFridgeRes.fridgeId = fridgeId;
    selectFridgeRes.fridgeName = fridgeName;
    return selectFridgeRes;
  }
}
