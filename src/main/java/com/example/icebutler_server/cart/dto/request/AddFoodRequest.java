package com.example.icebutler_server.cart.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(name = "AddFoodRequest", description = "식품 추가 요청 정보")
public class AddFoodRequest {
    @Schema(name = "foodName", description = "식품명")
    private String foodName;
    @Schema(name = "foodCategory", description = "식품 카테고리")
    private String foodCategory;
}
