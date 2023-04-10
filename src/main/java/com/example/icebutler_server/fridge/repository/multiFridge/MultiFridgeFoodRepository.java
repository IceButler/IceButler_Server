package com.example.icebutler_server.fridge.repository.multiFridge;

import com.example.icebutler_server.food.entity.FoodCategory;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridgeFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MultiFridgeFoodRepository extends JpaRepository<MultiFridgeFood, Long> {
    List<MultiFridgeFood> findByFood_FoodCategoryAndIsEnableOrderByShelfLife(FoodCategory foodCategory, Boolean status);
    List<MultiFridgeFood> findByIsEnableOrderByShelfLife(Boolean status);

}
