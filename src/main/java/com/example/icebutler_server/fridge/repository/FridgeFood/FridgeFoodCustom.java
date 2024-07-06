package com.example.icebutler_server.fridge.repository.FridgeFood;

import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.food.entity.FoodCategory;
import com.example.icebutler_server.food.entity.FoodDeleteStatus;
import com.example.icebutler_server.fridge.dto.response.FridgeDiscardRes;
import com.example.icebutler_server.fridge.entity.Fridge;
import com.example.icebutler_server.fridge.entity.FridgeFood;
import com.example.icebutler_server.fridge.entity.FridgeUser;

import java.util.List;

public interface FridgeFoodCustom {
    Long findByDeleteCategoryForStatistics(FoodDeleteStatus deleteCategory, Fridge fridge, FoodCategory category, Integer year, Integer month);
    FridgeDiscardRes findByFridgeForDisCardFood(Fridge fridge);
    List<Food> findByUserForFridgeRecipeFoodList(Fridge fridge);
    void deleteOwnerByFridgeUser(FridgeUser fridgeUser);

    List<FridgeFood> findByActiveAndShelfLifeLimit3();
}
