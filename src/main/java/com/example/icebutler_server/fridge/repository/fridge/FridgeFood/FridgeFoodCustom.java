package com.example.icebutler_server.fridge.repository.fridge.FridgeFood;

import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.food.entity.FoodCategory;
import com.example.icebutler_server.food.entity.FoodDeleteStatus;
import com.example.icebutler_server.fridge.dto.fridge.response.FridgeDiscardRes;
import com.example.icebutler_server.fridge.entity.fridge.Fridge;
import com.example.icebutler_server.fridge.entity.fridge.FridgeUser;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridge;

import java.util.List;

public interface FridgeFoodCustom {
    Long findByDeleteCategoryForStatistics(FoodDeleteStatus deleteCategory, Fridge fridge, FoodCategory category, Integer year, Integer month);
    FridgeDiscardRes findByFridgeForDisCardFood(Fridge fridge);
    List<Food> findByUserForFridgeRecipeFoodList(Fridge fridge);
    void deleteOwnerByFridgeUser(FridgeUser fridgeUser);
}
