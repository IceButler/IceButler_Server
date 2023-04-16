package com.example.icebutler_server.fridge.dto.fridge.response;

import com.example.icebutler_server.fridge.dto.fridge.assembler.FridgeUtils;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridgeFood;
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
  private String day;
  private String owner;
  private String memo;
  private String imgUrl;

  public static FridgeFoodRes toDto(MultiFridgeFood fridgeFood) {
    String owner;

    if(fridgeFood.getOwner()==null) owner = null;
    else owner = fridgeFood.getOwner().getNickname();

    return FridgeFoodRes.builder()
            .fridgeFoodIdx(fridgeFood.getMultiFridgeFoodIdx())
            .foodIdx(fridgeFood.getFood().getFoodIdx())
            .foodName(fridgeFood.getFood().getFoodName())
            .foodDetailName(fridgeFood.getFoodDetailName())
            .foodCategory(fridgeFood.getFood().getFoodCategory().getName())
            .shelfLife(fridgeFood.getShelfLife().format(DateTimeFormatter.ISO_DATE))
            .day(FridgeUtils.calShelfLife(fridgeFood.getShelfLife()))
            .owner(owner)
            .memo(fridgeFood.getMemo())
            .imgUrl(fridgeFood.getFridgeFoodImgKey())
            .build();
  }
}