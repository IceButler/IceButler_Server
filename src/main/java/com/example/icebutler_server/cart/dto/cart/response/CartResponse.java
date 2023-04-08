package com.example.icebutler_server.cart.dto.cart.response;

import com.example.icebutler_server.cart.entity.cart.CartFood;
import com.example.icebutler_server.food.dto.response.FoodResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Data
public class CartResponse {
    private List<FoodResponse> cartFoods = new ArrayList<>();

    public static CartResponse toDto(List<CartFood> cartFood) {
        CartResponse cartResponse = new CartResponse();
        cartResponse.cartFoods = cartFood.stream()
                .map((cf) -> FoodResponse.toDto(cf.getFood())).collect(Collectors.toList());
        return cartResponse;
    }
}
