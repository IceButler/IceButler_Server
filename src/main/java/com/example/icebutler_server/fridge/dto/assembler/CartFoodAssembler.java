package com.example.icebutler_server.fridge.dto.assembler;

import com.example.icebutler_server.fridge.entity.Cart;
import com.example.icebutler_server.fridge.entity.CartFood;
import com.example.icebutler_server.fridge.entity.Food;
import org.springframework.stereotype.Component;

@Component
public class CartFoodAssembler {

    public CartFood toEntity(Cart cart, Food food) {
        return CartFood.builder()
                .cart(cart)
                .food(food).build();
    }
}
