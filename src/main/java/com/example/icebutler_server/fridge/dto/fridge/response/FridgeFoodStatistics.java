package com.example.icebutler_server.fridge.dto.fridge.response;

import com.example.icebutler_server.food.entity.FoodCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FridgeFoodStatistics {
    private String foodCategory;
    private String foodImgKey;
    private Double percentage;
    private Integer count;



}
