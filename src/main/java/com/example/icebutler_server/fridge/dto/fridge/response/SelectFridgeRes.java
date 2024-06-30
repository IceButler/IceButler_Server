package com.example.icebutler_server.fridge.dto.fridge.response;

import com.example.icebutler_server.fridge.entity.fridge.Fridge;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "SelectFridgeRes", description = "냉장고 정보")
public class SelectFridgeRes {
  @Schema(name = "fridgeName", description = "냉장고 이름")
  private String fridgeName;
  @Schema(name = "fridgeIdx", description = "냉장고 ID")
  private Long fridgeIdx;
  @Schema(name = "category", description = "냉장고 종류")
  private String category;

  public static SelectFridgeRes toDto(String fridgeName, Long fridgeIdx, String category) {
    SelectFridgeRes selectFridgeRes = new SelectFridgeRes();
    selectFridgeRes.fridgeIdx = fridgeIdx;
    selectFridgeRes.fridgeName = fridgeName;
    selectFridgeRes.category = category;
    return selectFridgeRes;
  }
}
