package com.example.icebutler_server.fridge.dto.fridge.response;

import com.example.icebutler_server.fridge.dto.fridge.assembler.FridgeUtils;
import com.example.icebutler_server.fridge.entity.fridge.FridgeFood;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridgeFood;
import com.example.icebutler_server.global.util.AwsS3ImageUrlUtil;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FridgeMainRes {
  private FridgeDiscardRes fridgeDiscard;
  private List<FridgeFoodsRes> foodList = new ArrayList<>();

  public static FridgeMainRes toFridgeDto(FridgeDiscardRes fridgeDiscard, List<FridgeFood> fridgeFoods) {
    return new FridgeMainRes(isNullFoodDiscardRes(fridgeDiscard), fridgeFoods.stream()
            .map(ff -> new FridgeFoodsRes(ff.getFridgeFoodIdx(), ff.getFood().getFoodName(), AwsS3ImageUrlUtil.toUrl(ff.getFood().getFoodImgKey()), FridgeUtils.calShelfLife(ff.getShelfLife())))
            .collect(Collectors.toList()));
  }

  public static FridgeMainRes toMultiDto(FridgeDiscardRes fridgeDiscard, List<MultiFridgeFood> fridgeFoods) {
    return new FridgeMainRes(isNullFoodDiscardRes(fridgeDiscard), fridgeFoods.stream()
            .map(ff -> new FridgeFoodsRes(ff.getMultiFridgeFoodIdx(), ff.getFood().getFoodName(), AwsS3ImageUrlUtil.toUrl(ff.getFood().getFoodImgKey()), FridgeUtils.calShelfLife(ff.getShelfLife())))
            .collect(Collectors.toList()));
  }

  private static FridgeDiscardRes isNullFoodDiscardRes(FridgeDiscardRes fridgeDiscard){
    return fridgeDiscard != null ? fridgeDiscard : new FridgeDiscardRes();
  }

}