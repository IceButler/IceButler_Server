package com.example.icebutler_server.cart.dto.assembler;

import com.example.icebutler_server.cart.entity.Cart;
import com.example.icebutler_server.cart.entity.CartFood;
import com.example.icebutler_server.food.entity.Food;
import org.springframework.stereotype.Component;

@Component
public class CartFoodAssembler {

    public CartFood toEntity(Cart cart, Food food) {
        return CartFood.builder()
                .cart(cart)
                .food(food)
                .build();
    }
}
