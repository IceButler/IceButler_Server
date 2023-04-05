package com.example.icebutler_server.fridge.repository;

import com.example.icebutler_server.fridge.entity.CartFood;

import java.util.List;

public interface CartFoodQuerydslRepository {
    List<CartFood> findByCardIdxAndFoodIdxIn(Long cartIdx, List<Long> foodIdxes);
}
