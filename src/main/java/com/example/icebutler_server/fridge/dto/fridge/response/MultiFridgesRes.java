package com.example.icebutler_server.fridge.dto.fridge.response;

import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridge;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MultiFridgesRes {
  private String multiFridgeName;
  private Long multiFridgeIdx;

  public static MultiFridgesRes toDto(MultiFridge multiFridge) {
    MultiFridgesRes multiFridgesRes = new MultiFridgesRes();
    multiFridgesRes.multiFridgeName = multiFridge.getFridgeName();
    multiFridgesRes.multiFridgeIdx = multiFridge.getMultiFridgeIdx();
    return multiFridgesRes;
  }
}
