package com.example.icebutler_server.global.feign.event;

import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.food.entity.FoodCategory;
import com.example.icebutler_server.global.feign.dto.FoodReq;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateFoodEvent {
  private Long foodIdx;
  private String foodName;
  private String foodImgKey;
  private FoodCategory foodCategory;
  private UUID uuid;

  public static UpdateFoodEvent toEvent(Food food){
    UpdateFoodEvent foodJoinEvent = new UpdateFoodEvent();
    foodJoinEvent.foodIdx = food.getFoodIdx();
    foodJoinEvent.foodName = food.getFoodName();
    foodJoinEvent.foodImgKey = food.getFoodImgKey();
    foodJoinEvent.foodCategory = food.getFoodCategory();
    foodJoinEvent.uuid = food.getUuid();
    return foodJoinEvent;
  }

  public FoodReq toDto() {
    return FoodReq.builder()
            .foodIdx(foodIdx)
            .foodName(foodName)
            .foodImgKey(foodImgKey)
            .foodCategory(foodCategory)
            .uuid(uuid)
            .build();
  }
}
