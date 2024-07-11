package com.example.icebutler_server.fridge.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "FridgeFoodStatistics", description = "냉장고 식품 삭제 통계 정보")
public class FridgeFoodStatistics {
    @Schema(name = "foodCategory", description = "식품 카테고리")
    private String foodCategory;
    @Schema(name = "foodCategoryImgUrl", description = "식품 카테고리 이미지 URL")
    private String foodCategoryImgUrl;
    @Schema(name = "percentage", description = "삭제 비율")
    private Double percentage;
    @Schema(name = "count", description = "삭제 개수")
    private Integer count;
}
