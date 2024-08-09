package com.example.icebutler_server.fridge.dto.response;

import com.example.icebutler_server.global.util.FridgeUtils;
import com.example.icebutler_server.fridge.entity.FridgeFood;
import com.example.icebutler_server.global.util.AwsS3ImageUrlUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "FridgeMainRes", description = "냉장고 식품 정보")
public class FridgeMainRes {
  @Schema(name = "foodList", description = "냉장고 식품 정보")
  private List<FridgeFoodsRes> foodList = new ArrayList<>();

  public static FridgeMainRes toFridgeDto(List<FridgeFood> fridgeFoods) {
    return new FridgeMainRes(fridgeFoods.stream()
            .map(ff -> new FridgeFoodsRes(ff.getId(), ff.getFood().getFoodName(), AwsS3ImageUrlUtil.toUrl(ff.getFood().getFoodImgKey()), FridgeUtils.calShelfLife(ff.getExpirationDate())))
            .collect(Collectors.toList()));
  }

  private static FridgeDiscardRes isNullFoodDiscardRes(FridgeDiscardRes fridgeDiscard){
    return fridgeDiscard != null ? fridgeDiscard : new FridgeDiscardRes();
  }

}