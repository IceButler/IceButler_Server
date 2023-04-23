package com.example.icebutler_server.food.dto.assembler;

import com.example.icebutler_server.cart.dto.cart.request.AddFoodRequest;
import com.example.icebutler_server.food.dto.response.FoodRes;
import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.food.entity.FoodCategory;
import com.example.icebutler_server.fridge.dto.fridge.request.FridgeFoodReq;
import com.example.icebutler_server.global.util.AwsS3ImageUrlUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.example.icebutler_server.global.util.Constant.Food.*;

@Component
@RequiredArgsConstructor
public class FoodAssembler {

  public Food toEntity(FridgeFoodReq request){
    String foodImageKey = IMG_FOLDER + request.getFoodCategory()+ ICON_EXTENSION;
    return Food.builder()
            .foodName(request.getFoodName())
            .foodCategory(FoodCategory.getFoodCategoryByName(request.getFoodCategory()))
            .foodImgKey(foodImageKey)
            .build();
  }

  public Food toEntity(AddFoodRequest request) {
    String foodImageKey = IMG_FOLDER + request.getFoodCategory()+ ICON_EXTENSION;
    return Food.builder()
            .foodName(request.getFoodName())
            .foodImgKey(foodImageKey)
            .foodCategory(FoodCategory.getFoodCategoryByName(request.getFoodCategory()))
            .build();
  }
}
