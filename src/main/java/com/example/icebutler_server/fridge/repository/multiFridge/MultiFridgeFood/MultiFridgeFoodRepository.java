package com.example.icebutler_server.fridge.repository.multiFridge.MultiFridgeFood;

import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.food.entity.FoodCategory;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridge;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridgeFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MultiFridgeFoodRepository extends JpaRepository<MultiFridgeFood, Long>, MultiFridgeFoodCustom{
    List<MultiFridgeFood> findByMultiFridgeAndFood_FoodCategoryAndIsEnableOrderByShelfLife(MultiFridge multiFridge,FoodCategory foodCategory, Boolean isEnable);
    List<MultiFridgeFood> findByMultiFridgeAndIsEnableOrderByShelfLife(MultiFridge multiFridge, Boolean status);
    Optional<MultiFridgeFood> findByMultiFridgeFoodIdxAndMultiFridgeAndIsEnable(Long multiFridgeFoodIdx, MultiFridge multiFridge, Boolean isEnable);
    void deleteByMultiFridge(MultiFridge multiFridge);
    void deleteByFood(Food food);
}
