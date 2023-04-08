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
public class BarcodeFoodRes {

    private Long foodIdx;
    private String foodName;
    private String foodDetailName;
    private String foodCategory;

    public static BarcodeFoodRes toDto(Food food, String foodDetailName) {
        return BarcodeFoodRes.builder()
                .foodIdx(food.getFoodIdx())
                .foodName(food.getFoodName())
                .foodDetailName(foodDetailName)
                .foodCategory(food.getFoodCategory().getName())
                .build();
    }

    public static BarcodeFoodRes toDto(String foodName, String foodDetailName, String foodCategory) {
        return BarcodeFoodRes.builder()
                .foodName(foodName)
                .foodDetailName(foodDetailName)
                .foodCategory(foodCategory)
                .build();
    }
}
