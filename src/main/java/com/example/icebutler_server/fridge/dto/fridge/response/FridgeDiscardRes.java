package com.example.icebutler_server.fridge.dto.fridge.response;

import com.example.icebutler_server.food.entity.FoodCategory;
import com.example.icebutler_server.global.util.AwsS3ImageUrlUtil;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(name = "FridgeDiscardRes", description = "냉장고 낭비된 식품 정보")
public class FridgeDiscardRes {
  @Schema(name = "discardFoodCategory", description = "낭비된 식품 카테고리")
  private String discardFoodCategory;
  @Schema(name = "discardFoodImgUrl", description = "낭비된 식품 카테고리 이미지 URL")
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