package com.example.icebutler_server.cart.dto.cart.response;

import com.example.icebutler_server.cart.entity.cart.CartFood;
import com.example.icebutler_server.cart.entity.multiCart.MultiCartFood;
import com.example.icebutler_server.food.dto.response.FoodResponse;
import com.example.icebutler_server.food.entity.FoodCategory;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Data
@Getter
public class CartResponse {
    private String category;
    private List<FoodResponse> cartFoods;

    public CartResponse(String category, List<FoodResponse> cartFoods) {
        this.category = category;
        this.cartFoods = cartFoods;
    }

    public static CartResponse doDto(List<CartFood> cartFoods, FoodCategory category) {
        CartResponse cartResponse = new CartResponse();
        cartResponse.category = category.getName();
        cartResponse.cartFoods = cartFoods.stream()
                .map(cf -> FoodResponse.toDto(cf.getFood())).collect(Collectors.toList());
        return cartResponse;
    }

    public static CartResponse doMultiDto(List<MultiCartFood> cartFoods, FoodCategory category) {
        CartResponse cartResponse = new CartResponse();
        cartResponse.category = category.getName();
        cartResponse.cartFoods = cartFoods.stream()
                .map(cf -> FoodResponse.toDto(cf.getFood())).collect(Collectors.toList());
        return cartResponse;
    }
}
