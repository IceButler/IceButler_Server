package com.example.icebutler_server.food.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BarcodeFoodRes {

    private String foodDetailName;

    public static BarcodeFoodRes toDto(String foodDetailName) {
        return BarcodeFoodRes.builder()
                .foodDetailName(foodDetailName)
                .build();
    }
}
