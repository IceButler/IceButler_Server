package com.example.icebutler_server.food.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(name = "BarcodeFoodRes", description = "식품 바코드 검색 정보")
public class BarcodeFoodRes {
    @Schema(name = "식품명")
    private String foodDetailName;

    public static BarcodeFoodRes toDto(String foodDetailName) {
        return BarcodeFoodRes.builder()
                .foodDetailName(foodDetailName)
                .build();
    }
}
