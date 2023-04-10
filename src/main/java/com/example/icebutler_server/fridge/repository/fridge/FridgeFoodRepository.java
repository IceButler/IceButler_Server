package com.example.icebutler_server.fridge.repository.fridge;

import com.example.icebutler_server.food.entity.FoodCategory;
import com.example.icebutler_server.fridge.entity.fridge.FridgeFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FridgeFoodRepository extends JpaRepository<FridgeFood, Long> {
    List<FridgeFood> findByFood_FoodCategoryAndIsEnableOrderByShelfLife(FoodCategory foodCategory, Boolean status);
    List<FridgeFood> findByIsEnableOrderByShelfLife(Boolean status);

}
