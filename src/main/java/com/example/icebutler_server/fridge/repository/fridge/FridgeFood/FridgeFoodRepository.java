package com.example.icebutler_server.fridge.repository.fridge.FridgeFood;

import com.example.icebutler_server.food.entity.FoodCategory;
import com.example.icebutler_server.fridge.entity.fridge.Fridge;
import com.example.icebutler_server.fridge.entity.fridge.FridgeFood;
import com.example.icebutler_server.fridge.repository.fridge.FridgeFood.FridgeFoodCustom;
import com.example.icebutler_server.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FridgeFoodRepository extends JpaRepository<FridgeFood, Long>, FridgeFoodCustom {
    List<FridgeFood> findByFood_FoodCategoryAndIsEnableOrderByShelfLife(FoodCategory foodCategory, Boolean status);
    List<FridgeFood> findByIsEnableOrderByShelfLife(Boolean status);
    Optional<FridgeFood> findByFridgeFoodIdxAndOwnerAndFridgeAndIsEnable(Long fridgeFoodIdx, User owner, Fridge fridge, Boolean isEnable);
    Optional<FridgeFood> findByFridgeFoodIdxAndFridgeAndIsEnable(Long fridgeFoodIdx,  Fridge fridge, Boolean isEnable);
    List<FridgeFood> findByFridgeAndFood_FoodCategoryAndIsEnableOrderByShelfLife(Fridge fridge, FoodCategory foodCategory, Boolean status);
    List<FridgeFood> findByFridgeAndIsEnableOrderByShelfLife(Fridge fridge, Boolean status);
}
