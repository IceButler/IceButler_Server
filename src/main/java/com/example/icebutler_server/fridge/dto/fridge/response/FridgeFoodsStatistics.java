package com.example.icebutler_server.fridge.dto.fridge.response;

import com.example.icebutler_server.food.entity.FoodCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FridgeFoodsStatistics {

    private List<FridgeFoodStatistics> foodStatisticsList;

    public static FridgeFoodsStatistics toDto(List<FridgeFoodStatistics> list){
        return new FridgeFoodsStatistics(list);
    }

}