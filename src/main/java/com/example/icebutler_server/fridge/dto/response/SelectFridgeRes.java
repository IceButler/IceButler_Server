package com.example.icebutler_server.fridge.dto.response;

import lombok.Data;

@Data
public class SelectFridgeRes {
  private String fridgeName;
  private Long fridgeIdx;

  public static SelectFridgeRes toDto(String fridgeName, Long fridgeIdx) {
    SelectFridgeRes selectFridgeRes = new SelectFridgeRes();
    selectFridgeRes.fridgeIdx = fridgeIdx;
    selectFridgeRes.fridgeName = fridgeName;
    return selectFridgeRes;
  }
}
