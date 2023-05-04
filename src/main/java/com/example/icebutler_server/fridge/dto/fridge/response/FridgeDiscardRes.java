package com.example.icebutler_server.fridge.dto.fridge.response;

import com.example.icebutler_server.food.entity.FoodCategory;
import com.example.icebutler_server.global.util.AwsS3ImageUrlUtil;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FridgeDiscardRes {
  private String discardFoodCategory;
  private String discardFoodImgUrl;

  @QueryProjection
  public FridgeDiscardRes(FoodCategory discardFoodCategory, String discardFoodImgUrl) {
    this.discardFoodCategory = isNullFoodCategory(discardFoodCategory);
    this.discardFoodImgUrl = AwsS3ImageUrlUtil.toUrl(discardFoodImgUrl);
  }

  private static String isNullFoodCategory(FoodCategory foodCategory){
    return foodCategory != null ? foodCategory.getName() : null;
  }
}