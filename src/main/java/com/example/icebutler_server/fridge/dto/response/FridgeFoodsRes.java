package com.example.icebutler_server.fridge.dto.response;

import com.example.icebutler_server.global.util.FridgeUtils;
import com.example.icebutler_server.fridge.entity.FridgeFood;
import com.example.icebutler_server.global.util.AwsS3ImageUrlUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "FridgeFoodsRes", description = "냉장고 식품 정보")
public class FridgeFoodsRes {
  @Schema(name = "fridgeFoodId", description = "냉장고 식품 ID")
  private Long fridgeFoodId;
  @Schema(name = "foodName", description = "냉장고 식품 이름")
  private String foodName;
  @Schema(name = "foodImgUrl", description = "냉장고 식품 이미지 URL")
  private String foodImgUrl;
  @Schema(name = "shelfLife", description = "식품 유효기한")
  private int shelfLife;

  public static FridgeFoodsRes toDto(FridgeFood fridgeFood) {
    return FridgeFoodsRes.builder()
            .fridgeFoodId(fridgeFood.getId())
            .foodName(fridgeFood.getFood().getFoodName())
            .foodImgUrl(AwsS3ImageUrlUtil.toUrl(fridgeFood.getFood().getFoodImgKey()))
            .shelfLife(FridgeUtils.calShelfLife(fridgeFood.getShelfLife()))
            .build();
  }
}