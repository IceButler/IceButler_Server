package com.example.icebutler_server.fridge.dto.response;

import com.example.icebutler_server.fridge.dto.assembler.FridgeUtils;
import com.example.icebutler_server.fridge.entity.FridgeFood;
import com.example.icebutler_server.global.util.AwsS3ImageUrlUtil;
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
  private String foodImgUrl;
  private int shelfLife;

  public static FridgeFoodsRes toDto(FridgeFood fridgeFood) {
    return FridgeFoodsRes.builder()
            .fridgeFoodIdx(fridgeFood.getId())
            .foodName(fridgeFood.getFood().getFoodName())
            .foodImgUrl(AwsS3ImageUrlUtil.toUrl(fridgeFood.getFood().getFoodImgKey()))
            .shelfLife(FridgeUtils.calShelfLife(fridgeFood.getShelfLife()))
            .build();
  }
}