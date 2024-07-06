package com.example.icebutler_server.cart.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
@Schema(name = "RemoveFoodFromCartRequest", description = "장바구니 식품 삭제 요청 정보")
public class RemoveFoodFromCartRequest {
    @Schema(name = "foodIdxes", description = "식품 ID 목록")
    private List<Long> foodIdxes;
}
