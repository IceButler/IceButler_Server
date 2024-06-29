package com.example.icebutler_server.food.dto.response;

import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.global.util.AwsS3ImageUrlUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Schema(name = "FoodResponse", description = "장바구니 내 식품 정보")
public class FoodResponse {
    @Schema(name = "식품 ID")
    private Long foodIdx;
    @Schema(name = "식품명")
    private String foodName;
    @Schema(name = "식품 이미지 URL")
    private String foodImgUrl;

    public static FoodResponse toDto(Food food) {
        FoodResponse foodResponse = new FoodResponse();
        foodResponse.foodIdx = food.getId();
        foodResponse.foodName = food.getFoodName();
        foodResponse.foodImgUrl = AwsS3ImageUrlUtil.toUrl(food.getFoodImgKey());
        return foodResponse;
    }
}
