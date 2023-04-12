package com.example.icebutler_server.fridge.dto.fridge.response;

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
}
