package com.example.icebutler_server.food.dto.response;

import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.global.util.AwsS3ImageUrlUtil;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class FoodResponse {

    private Long foodIdx;
    private String foodName;
    private String foodImgUrl;


    public static FoodResponse toDto(Food food) {
        FoodResponse foodResponse = new FoodResponse();
        foodResponse.foodIdx = food.getFoodIdx();
        foodResponse.foodName = food.getFoodName();
        foodResponse.foodImgUrl = AwsS3ImageUrlUtil.toUrl(food.getFoodImgKey());
        return foodResponse;
    }

}
