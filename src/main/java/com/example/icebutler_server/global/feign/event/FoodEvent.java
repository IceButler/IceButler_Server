package com.example.icebutler_server.global.feign.event;

import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.food.entity.FoodCategory;
import com.example.icebutler_server.global.feign.dto.FoodReq;
import lombok.Getter;

import java.util.UUID;

@Getter
public class FoodEvent {
  private Long foodId;
  private String foodName;
  private String foodImgKey;
  private FoodCategory foodCategory;
  private UUID uuid;

  public static FoodEvent toEvent(Food food){
    FoodEvent foodJoinEvent = new FoodEvent();
    foodJoinEvent.foodId = food.getId();
    foodJoinEvent.foodName = food.getFoodName();
    foodJoinEvent.foodImgKey = food.getFoodImgKey();
    foodJoinEvent.foodCategory = food.getFoodCategory();
    foodJoinEvent.uuid = food.getUuid();
    return foodJoinEvent;
  }

  public FoodReq toDto() {
    return FoodReq.builder()
            .foodId(foodId)
            .foodName(foodName)
            .foodImgKey(foodImgKey)
            .foodCategory(foodCategory)
            .uuid(uuid)
            .build();
  }
}
