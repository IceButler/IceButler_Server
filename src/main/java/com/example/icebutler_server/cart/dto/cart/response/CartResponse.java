package com.example.icebutler_server.cart.dto.cart.response;

import com.example.icebutler_server.cart.entity.cart.CartFood;
import com.example.icebutler_server.cart.entity.multiCart.MultiCartFood;
import com.example.icebutler_server.food.dto.response.FoodResponse;
import com.example.icebutler_server.food.entity.FoodCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Data
@Getter
@Schema(name = "CartResponse", description = "장바구니 정보")
public class CartResponse {
    @Schema(name = "category", description = "식품 카테고리")
    private String category;
    @Schema(name = "cartFoods", description = "장바구니 내 식품목록")
    private List<FoodResponse> cartFoods;

    @Builder
    public CartResponse(String category, List<FoodResponse> cartFoods) {
        this.category = category;
        this.cartFoods = cartFoods;
    }

    public static CartResponse toDto(List<CartFood> cartFoods, FoodCategory category) {
        CartResponse cartResponse = new CartResponse();
        cartResponse.category = category.getName();
        cartResponse.cartFoods = cartFoods.stream()
                .map(cf -> FoodResponse.toDto(cf.getFood())).collect(Collectors.toList());
        return cartResponse;
    }

    public static CartResponse toMultiDto(List<MultiCartFood> cartFoods, FoodCategory category) {
        CartResponse cartResponse = new CartResponse();
        cartResponse.category = category.getName();
        cartResponse.cartFoods = cartFoods.stream()
                .map(cf -> FoodResponse.toDto(cf.getFood())).collect(Collectors.toList());
        return cartResponse;
    }
}
