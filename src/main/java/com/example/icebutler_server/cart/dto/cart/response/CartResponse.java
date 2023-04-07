package com.example.icebutler_server.cart.dto.cart.response;

import com.example.icebutler_server.cart.entity.cart.Cart;
import com.example.icebutler_server.food.dto.response.FoodRes;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Data
public class CartResponse {
    private List<FoodRes> foods = new ArrayList<>();

    public static CartResponse toDto(Cart cart) {
        CartResponse cartResponse = new CartResponse();
        cartResponse.foods = cart.getCartFoods().stream()
                .map((cr) -> FoodRes.toDto(cr.getFood())).collect(Collectors.toList());
        return cartResponse;
    }
}
