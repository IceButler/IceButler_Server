package com.example.icebutler_server.food.dto.assembler;

import com.example.icebutler_server.food.dto.response.FoodRes;
import com.example.icebutler_server.food.entity.Food;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FoodAssembler {

//  public Food toEntity(FoodReq foodReq) {
//    return Food.builder()
//  }

  public FoodRes toDto(Food food) {
    return FoodRes.builder()
            .foodIdx(food.getFoodIdx())
            .foodName(food.getFoodName())
            .foodCategory(food.getFoodCategory().getName())
            .foodIconName(food.getFoodIconName())
            .build();
  }
}
