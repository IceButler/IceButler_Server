package com.example.icebutler_server.fridge.dto.fridge.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FridgeFoodReq {
  private String foodName;
  private String foodDetailName;
  private String foodCategory;
  private String shelfLife;
  private Long ownerIdx;
  private String memo;
  private String imgUrl;
}
