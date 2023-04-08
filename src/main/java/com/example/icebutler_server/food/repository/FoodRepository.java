package com.example.icebutler_server.food.repository;

import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.food.entity.FoodCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findAllByFoodIdxIn(List<Long> foodIdxes);
    List<Food> findAllByFoodCategory(FoodCategory foodCategory);
    Food findByFoodName(String foodName);
}
