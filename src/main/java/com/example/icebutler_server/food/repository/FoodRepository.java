package com.example.icebutler_server.food.repository;

import com.example.icebutler_server.admin.dto.response.SearchFoodsResponse;
import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.food.entity.FoodCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FoodRepository extends JpaRepository<Food, Long> {
  List<Food> findAllByFoodIdxIn(List<Long> foodIdxes);

  List<Food> findAllByFoodCategory(FoodCategory foodCategory);

  Optional<Food> findByFoodName(String foodName);

  Food findByFoodNameAndFoodCategory(String foodName, FoodCategory foodCategory);

  List<Food> findByFoodNameContainsAndFoodCategory(String foodName, FoodCategory foodCategory);

  List<Food> findByFoodNameContains(String foodName);

  Optional<Food> findByFoodIdxAndIsEnable(Long foodIdx, boolean status);

  Page<Food> findByFoodNameContainsAndIsEnable(String cond, boolean status, Pageable pageable);

  Food findByFoodNameAndIsEnable(String foodName, boolean status);

//  Page<Food> findByIsEnableOrderByCreatedAt(boolean status, Pageable pageable);
  Page<Food> findByIsEnableOrderByUpdateAtDesc(boolean status, Pageable pageable);
}
