package com.example.icebutler_server.food.dto.assembler;

import com.example.icebutler_server.cart.dto.cart.request.AddFoodRequest;
import com.example.icebutler_server.food.dto.response.FoodRes;
import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.food.entity.FoodCategory;
import com.example.icebutler_server.fridge.dto.fridge.request.FridgeFoodReq;
import com.example.icebutler_server.global.util.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.example.icebutler_server.global.util.Constant.Food.ICON_EXTENSION;

@Component
@RequiredArgsConstructor
public class FoodAssembler {

  public Food toEntity(FridgeFoodReq fridgeFoodReq){
    return Food.builder()
            .foodName(fridgeFoodReq.getFoodName())
            .foodCategory(FoodCategory.getFoodCategoryByName(fridgeFoodReq.getFoodCategory()))
            .foodIconName(fridgeFoodReq.getFoodCategory())
            .build();
  }

  public FoodRes toDto(Food food) {
    return FoodRes.builder()
            .foodIdx(food.getFoodIdx())
            .foodName(food.getFoodName())
            .foodCategory(food.getFoodCategory().getName())
            .foodIconName(food.getFoodIconName())
            .build();
  }

  public Food toEntity(AddFoodRequest request) {
    return Food.builder()
            .foodName(request.getFoodName())
            .foodIconName(request.getFoodCategory()+ ICON_EXTENSION)
            .foodCategory(FoodCategory.getFoodCategoryByName(request.getFoodCategory()))
            .build();
  }
}
