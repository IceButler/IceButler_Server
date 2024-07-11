package com.example.icebutler_server.fridge.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "FridgeFoodsStatistics", description = "식품 카테고리별 삭제 통계 목록")
public class FridgeFoodsStatistics {

    @Schema(name = "foodStatisticsList", description = "식품 삭제 통계 목록")
    private List<FridgeFoodStatistics> foodStatisticsList;

    public static FridgeFoodsStatistics toDto(List<FridgeFoodStatistics> list) {
        return new FridgeFoodsStatistics(list);
    }

}