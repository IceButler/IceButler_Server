package com.example.icebutler_server.fridge.repository.FridgeFood;

import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.fridge.entity.Fridge;
import com.example.icebutler_server.fridge.entity.FridgeFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FridgeFoodRepository extends JpaRepository<FridgeFood, Long>, FridgeFoodCustom {
    Optional<FridgeFood> findByIdAndFridgeAndIsEnable(Long fridgeFoodId, Fridge fridge, Boolean status);
    void deleteByFridge(Fridge fridge);
    void deleteByFood(Food food);
}
