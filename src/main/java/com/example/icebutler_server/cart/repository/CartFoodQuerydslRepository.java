package com.example.icebutler_server.cart.repository;

import com.example.icebutler_server.cart.entity.CartFood;

import java.util.List;

public interface CartFoodQuerydslRepository {
    List<CartFood> findByCartIdAndFoodIdIn(Long cartIdx, List<Long> foodIdxes);

}
