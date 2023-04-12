package com.example.icebutler_server.fridge.dto.fridge.response;

import com.example.icebutler_server.fridge.entity.fridge.Fridge;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FridgesRes {
  private String fridgeName;
  private Long fridgeIdx;

  public static FridgesRes toDto(Fridge fridge) {
    FridgesRes fridgesRes = new FridgesRes();
    fridgesRes.fridgeName = fridge.getFridgeName();
    fridgesRes.fridgeIdx = fridge.getFridgeIdx();
    return fridgesRes;
  }
}
