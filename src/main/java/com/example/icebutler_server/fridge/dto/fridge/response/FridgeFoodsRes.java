package com.example.icebutler_server.fridge.dto.fridge.response;

import com.example.icebutler_server.fridge.dto.fridge.assembler.FridgeUtils;
import com.example.icebutler_server.fridge.entity.fridge.FridgeFood;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridgeFood;
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
  @Schema(name = "fridgeFoodIdx", description = "냉장고 식품 ID")
  private Long fridgeFoodIdx;
  @Schema(name = "foodName", description = "냉장고 식품 이름")
  private String foodName;
  @Schema(name = "foodImgUrl", description = "냉장고 식품 이미지 URL")
  private String foodImgUrl;
  @Schema(name = "shelfLife", description = "식품 유효기한")
  private int shelfLife;

  public static FridgeFoodsRes toMultiDto(MultiFridgeFood fridgeFood) {
    return FridgeFoodsRes.builder()
            .fridgeFoodIdx(fridgeFood.getId())
            .foodName(fridgeFood.getFood().getFoodName())
            .foodImgUrl(AwsS3ImageUrlUtil.toUrl(fridgeFood.getFood().getFoodImgKey()))
            .shelfLife(FridgeUtils.calShelfLife(fridgeFood.getShelfLife()))
            .build();
  }

  public static FridgeFoodsRes toDto(FridgeFood fridgeFood) {
    return FridgeFoodsRes.builder()
            .fridgeFoodIdx(fridgeFood.getId())
            .foodName(fridgeFood.getFood().getFoodName())
            .foodImgUrl(AwsS3ImageUrlUtil.toUrl(fridgeFood.getFood().getFoodImgKey()))
            .shelfLife(FridgeUtils.calShelfLife(fridgeFood.getShelfLife()))
            .build();
  }
}