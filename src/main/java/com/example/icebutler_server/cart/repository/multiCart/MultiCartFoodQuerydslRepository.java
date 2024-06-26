package com.example.icebutler_server.cart.repository.multiCart;

import com.example.icebutler_server.cart.entity.multiCart.MultiCartFood;

import java.util.List;

public interface MultiCartFoodQuerydslRepository {
    List<MultiCartFood> findByCartIdAndFoodIdInAndIsEnable(Long cartIdx, List<Long> foodIdxes, Boolean status);
}
