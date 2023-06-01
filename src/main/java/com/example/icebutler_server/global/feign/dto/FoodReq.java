package com.example.icebutler_server.global.feign.dto;

import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.food.entity.FoodCategory;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
public class FoodReq {
  private Long foodIdx;
  private String foodName;
  private String foodImgKey;
  private FoodCategory foodCategory;
  private UUID uuid;

  @Builder
  public FoodReq(Long foodIdx, String foodName, String foodImgKey, FoodCategory foodCategory, UUID uuid) {
    this.foodIdx = foodIdx;
    this.foodName = foodName;
    this.foodImgKey = foodImgKey;
    this.foodCategory = foodCategory;
    this.uuid = uuid;
  }

  public static FoodReq toDto(Food food) {
    return FoodReq.builder()
            .foodIdx(food.getFoodIdx())
            .foodName(food.getFoodName())
            .foodImgKey(food.getFoodImgKey())
            .foodCategory(food.getFoodCategory())
            .uuid(food.getUuid())
            .build();
  }
}
