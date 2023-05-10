package com.example.icebutler_server.cart.dto.cart.assembler;

import com.example.icebutler_server.cart.entity.multiCart.MultiCart;
import com.example.icebutler_server.cart.entity.multiCart.MultiCartFood;
import com.example.icebutler_server.food.entity.Food;
import org.springframework.stereotype.Component;

@Component
public class MultiCartFoodAssembler {

    public MultiCartFood toEntity(MultiCart cart, Food food) {
        return MultiCartFood.builder()
                .multiCart(cart)
                .food(food)
                .build();
    }
}
