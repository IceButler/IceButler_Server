package com.example.icebutler_server.cart.repository.cart;

import com.example.icebutler_server.cart.entity.cart.Cart;
import com.example.icebutler_server.cart.entity.cart.CartFood;
import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.food.entity.FoodCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartFoodRepository extends JpaRepository<CartFood, Long>, CartFoodQuerydslRepository {
    List<CartFood> findByCartAndIsEnable(Cart cart, Boolean isEnable);
    List<CartFood> findByCartAndFood_FoodCategoryAndIsEnableOrderByCreatedAt(Cart cart, FoodCategory category, Boolean isEnable);
    void deleteByCart(Cart cart);
    void deleteByFood(Food food);
}
