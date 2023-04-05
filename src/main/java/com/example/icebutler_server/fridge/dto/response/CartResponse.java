package com.example.icebutler_server.fridge.dto.response;

import com.example.icebutler_server.fridge.entity.Cart;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Data
public class CartResponse {
    private List<FoodResponse> foods = new ArrayList<>();

    public static CartResponse toDto(Cart cart) {
        CartResponse cartResponse = new CartResponse();
        cartResponse.foods = cart.getCartFoods().stream()
                .map((cr) -> FoodResponse.toDto(cr.getFood())).collect(Collectors.toList());
        return cartResponse;
    }
}
