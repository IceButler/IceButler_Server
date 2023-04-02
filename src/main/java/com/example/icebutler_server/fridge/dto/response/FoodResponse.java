package com.example.icebutler_server.fridge.dto.response;

import com.example.icebutler_server.fridge.entity.Food;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class FoodResponse {

    private Long foodIdx;
    private String foodName;
    private String foodCategory;

    public static FoodResponse toDto(Food food) {
        FoodResponse foodResponse = new FoodResponse();
        foodResponse.foodIdx = food.getFoodIdx();
        foodResponse.foodName = food.getFoodName();
        foodResponse.foodCategory = food.getFoodCategory().getFoodCategory();
        return foodResponse;
    }
}
