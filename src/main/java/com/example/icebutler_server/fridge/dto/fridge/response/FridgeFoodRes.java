package com.example.icebutler_server.fridge.dto.fridge.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FridgeFoodRes {
  private Long fridgeFoodIdx;
  private Long foodIdx;
  private String foodName;
  private String foodDetailName;
  private String foodCategory;
  private String shelfLife;
  private String day;
  private String owner;
  private String memo;
  private String imgUrl;
}