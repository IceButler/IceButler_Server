package com.example.icebutler_server.food.dto.response;

import com.example.icebutler_server.food.entity.Food;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FoodRes {

    private Long foodIdx;
    private String foodName;
    private String foodCategory;
    private String foodIconName;

    public static FoodRes toDto(Food food) {
        return FoodRes.builder()
                .foodIdx(food.getFoodIdx())
                .foodName(food.getFoodName())
                .foodCategory(food.getFoodCategory().getName())
                .foodIconName(food.getFoodImgKey())
                .build();
    }
}
