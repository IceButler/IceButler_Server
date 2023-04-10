package com.example.icebutler_server.food.dto.response;

import com.example.icebutler_server.food.entity.Food;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class FoodResponse {

    private Long foodIdx;
    private String foodName;
    private String foodIconName;
    public static FoodResponse toDto(Food food) {
        FoodResponse foodResponse = new FoodResponse();
        foodResponse.foodIdx = food.getFoodIdx();
        foodResponse.foodName = food.getFoodName();
        foodResponse.foodIconName = food.getFoodIconName();
        return foodResponse;
    }

    public FoodResponse(Long foodIdx, String foodName, String foodIconName) {
        this.foodIdx = foodIdx;
        this.foodName = foodName;
        this.foodIconName = foodIconName;
    }
}
