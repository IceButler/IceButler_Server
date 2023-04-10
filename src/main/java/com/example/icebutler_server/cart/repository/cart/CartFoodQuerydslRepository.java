package com.example.icebutler_server.cart.repository.cart;

import com.example.icebutler_server.cart.entity.cart.Cart;
import com.example.icebutler_server.cart.entity.cart.CartFood;
import com.example.icebutler_server.food.entity.FoodCategory;

import java.util.List;

public interface CartFoodQuerydslRepository {
    List<CartFood> findByCartIdxAndFoodIdxIn(Long cartIdx, List<Long> foodIdxes);

}
