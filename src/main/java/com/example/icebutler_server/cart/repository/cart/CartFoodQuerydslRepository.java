package com.example.icebutler_server.cart.repository.cart;

import com.example.icebutler_server.cart.entity.cart.CartFood;

import java.util.List;

public interface CartFoodQuerydslRepository {
    List<CartFood> findByCartIdxAndFoodIdxIn(Long cartIdx, List<Long> foodIdxes);

}
