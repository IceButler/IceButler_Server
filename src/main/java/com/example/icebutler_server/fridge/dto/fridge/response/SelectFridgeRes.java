package com.example.icebutler_server.fridge.dto.fridge.response;

import com.example.icebutler_server.fridge.entity.fridge.Fridge;
import lombok.Data;

@Data
public class SelectFridgeRes {
  private String fridgeName;
  private Long fridgeIdx;
  private String category;

  public static SelectFridgeRes toDto(String fridgeName, Long fridgeIdx, String category) {
    SelectFridgeRes selectFridgeRes = new SelectFridgeRes();
    selectFridgeRes.fridgeIdx = fridgeIdx;
    selectFridgeRes.fridgeName = fridgeName;
    selectFridgeRes.category = category;
    return selectFridgeRes;
  }
}
