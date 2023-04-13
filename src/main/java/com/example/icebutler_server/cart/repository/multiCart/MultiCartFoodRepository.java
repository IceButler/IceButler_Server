package com.example.icebutler_server.cart.repository.multiCart;


import com.example.icebutler_server.cart.entity.multiCart.MultiCart;
import com.example.icebutler_server.cart.entity.multiCart.MultiCartFood;
import com.example.icebutler_server.food.entity.FoodCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MultiCartFoodRepository extends JpaRepository<MultiCartFood, Long>, MultiCartFoodQuerydslRepository {
    List<MultiCartFood> findByMultiCartAndFood_FoodCategoryAndIsEnableOrderByCreatedAt(MultiCart cart, FoodCategory category, Boolean status);
    List<MultiCartFood> findByMultiCartAndIsEnable(MultiCart cart, Boolean status);
}
