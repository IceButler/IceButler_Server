package com.example.icebutler_server.fridge.dto.fridge.response;

import com.example.icebutler_server.fridge.dto.fridge.assembler.FridgeUtils;
import com.example.icebutler_server.fridge.entity.fridge.FridgeFood;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridgeFood;
import com.example.icebutler_server.global.util.AwsS3ImageUrlUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

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
  private int day;
  private String owner;
  private String memo;
  private String imgUrl;

  public static FridgeFoodRes toDto(MultiFridgeFood fridgeFood) {
    return FridgeFoodRes.builder()
            .fridgeFoodIdx(fridgeFood.getMultiFridgeFoodIdx())
            .foodIdx(fridgeFood.getFood().getFoodIdx())
            .foodName(fridgeFood.getFood().getFoodName())
            .foodDetailName(fridgeFood.getFoodDetailName())
            .foodCategory(fridgeFood.getFood().getFoodCategory().getName())
            .shelfLife(fridgeFood.getShelfLife().format(DateTimeFormatter.ISO_DATE))
            .day(FridgeUtils.calShelfLife(fridgeFood.getShelfLife()))
            .owner(fridgeFood.getOwner() == null ? null : fridgeFood.getOwner().getNickname())
            .memo(fridgeFood.getMemo())
            .imgUrl(fridgeFood.getFridgeFoodImgKey() == null ? null : AwsS3ImageUrlUtil.toUrl(fridgeFood.getFridgeFoodImgKey()))
            .build();
  }

  public static FridgeFoodRes toDto(FridgeFood fridgeFood) {
    return FridgeFoodRes.builder()
            .fridgeFoodIdx(fridgeFood.getFridgeFoodIdx())
            .foodIdx(fridgeFood.getFood().getFoodIdx())
            .foodName(fridgeFood.getFood().getFoodName())
            .foodDetailName(fridgeFood.getFoodDetailName())
            .foodCategory(fridgeFood.getFood().getFoodCategory().getName())
            .shelfLife(fridgeFood.getShelfLife().format(DateTimeFormatter.ISO_DATE))
            .day(FridgeUtils.calShelfLife(fridgeFood.getShelfLife()))
            .owner(fridgeFood.getOwner() == null ? null : fridgeFood.getOwner().getNickname())
            .memo(fridgeFood.getMemo())
            .imgUrl(fridgeFood.getFridgeFoodImgKey() == null ? null : AwsS3ImageUrlUtil.toUrl(fridgeFood.getFridgeFoodImgKey()))
            .build();
  }
}