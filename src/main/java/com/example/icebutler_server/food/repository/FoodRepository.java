package com.example.icebutler_server.food.repository;

import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.food.entity.FoodCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FoodRepository extends JpaRepository<Food, Long>, FoodCustom {

  Optional<Food> findByFoodName(String foodName);

  Food findByFoodNameAndFoodCategory(String foodName, FoodCategory foodCategory);

  Optional<Food> findByIdAndIsEnable(Long foodId, boolean status);

  Page<Food> findByFoodNameContainsAndIsEnable(String cond, boolean status, Pageable pageable);

  Food findByFoodNameAndIsEnable(String foodName, boolean status);

  Page<Food> findByIsEnableOrderByUpdatedAtDesc(boolean status, Pageable pageable);
}
