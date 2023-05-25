package com.example.icebutler_server.fridge.dto.fridge.response;

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
    private String foodCategoryImgUrl;
    private Double percentage;
    private Integer count;



}
