package com.example.icebutler_server.cart.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
@Schema(name = "AddFoodToCartRequest", description = "장바구니 식품 추가 요청 정보")
public class AddFoodToCartRequest {
    @Schema(name = "foodRequests", description = "식품 목록")
    private List<AddFoodRequest> foodRequests;

}
