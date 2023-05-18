package com.example.icebutler_server.fridge.dto.fridge.response;

import com.example.icebutler_server.fridge.dto.fridge.assembler.FridgeUtils;
import com.example.icebutler_server.fridge.entity.fridge.FridgeFood;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridgeFood;
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

  public static FridgeFoodsRes toMultiDto(MultiFridgeFood fridgeFood) {
    return FridgeFoodsRes.builder()
            .fridgeFoodIdx(fridgeFood.getMultiFridgeFoodIdx())
            .foodName(fridgeFood.getFood().getFoodName())
            .foodImgUrl(AwsS3ImageUrlUtil.toUrl(fridgeFood.getFridgeFoodImgKey()))
            .shelfLife(FridgeUtils.calShelfLife(fridgeFood.getShelfLife()))
            .build();
  }

  public static FridgeFoodsRes toDto(FridgeFood fridgeFood) {
    return FridgeFoodsRes.builder()
            .fridgeFoodIdx(fridgeFood.getFridgeFoodIdx())
            .foodName(fridgeFood.getFood().getFoodName())
            .foodImgUrl(AwsS3ImageUrlUtil.toUrl(fridgeFood.getFridgeFoodImgKey()))
            .shelfLife(FridgeUtils.calShelfLife(fridgeFood.getShelfLife()))
            .build();
  }
}