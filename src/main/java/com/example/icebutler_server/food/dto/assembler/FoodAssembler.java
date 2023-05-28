package com.example.icebutler_server.food.dto.assembler;

import com.example.icebutler_server.cart.dto.cart.request.AddFoodRequest;
import com.example.icebutler_server.food.dto.request.FoodReq;
import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.food.entity.FoodCategory;
import com.example.icebutler_server.fridge.dto.fridge.request.FridgeFoodReq;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.example.icebutler_server.global.util.Constant.Food.*;

@Component
@RequiredArgsConstructor
public class FoodAssembler {

  public Food toEntity(FoodReq food){
    return Food.builder()
            .foodName(food.getFoodName())
            .foodImgKey(food.getFoodImgKey())
            // TODO 큐에서 전달 받을 때 category value 값을 받아서 우선 변경, 해당 부분 통일 필요
            .foodCategory(FoodCategory.valueOf(food.getFoodCategory()))
            .uuid(UUID.randomUUID())
            .build();
  }

  public Food toEntity(FridgeFoodReq request) {
    FoodCategory foodCategory = FoodCategory.getFoodCategoryByName(request.getFoodCategory());
    String foodImageKey = IMG_FOLDER + foodCategory.toString() + ICON_EXTENSION;
    return Food.builder()
            .foodName(request.getFoodName())
            .foodCategory(FoodCategory.getFoodCategoryByName(request.getFoodCategory()))
            .foodImgKey(foodImageKey)
            .uuid(UUID.randomUUID())
            .build();
  }

  public Food toEntity(AddFoodRequest request) {
    FoodCategory foodCategory = FoodCategory.getFoodCategoryByName(request.getFoodCategory());
    String foodImageKey = IMG_FOLDER + foodCategory.toString() + ICON_EXTENSION;
    return Food.builder()
            .foodName(request.getFoodName())
            .foodImgKey(foodImageKey)
            .foodCategory(foodCategory)
            .uuid(UUID.randomUUID())
            .build();
  }
}
