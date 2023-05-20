package com.example.icebutler_server.admin.dto.assembler;

import com.example.icebutler_server.admin.dto.request.ModifyFoodRequest;
import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.food.entity.FoodCategory;
import org.springframework.stereotype.Component;

@Component
public class AdminAssembler {
  public Food toUpdateFoodInfo(Food food, ModifyFoodRequest request) {
    if(request.getFoodImgKey() != null) food.toUpdateImgKey(request.getFoodImgKey());
    if(request.getFoodName() != null) food.toUpdateName(request.getFoodName());
    if(request.getFoodCategory() != null) food.toUpdateCategory(FoodCategory.getFoodCategoryByName(request.getFoodCategory()));
    return food;
  }
}
