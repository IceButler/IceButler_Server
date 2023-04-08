package com.example.icebutler_server.fridge.dto.fridge.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FridgeFoodsRes {
  private Long fridgeFoodIdx;
  private String foodName;
  private String foodIconName;
  private String shelfLife;
}