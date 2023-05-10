package com.example.icebutler_server.fridge.repository.multiFridge.MultiFridgeFood;

import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.food.entity.FoodCategory;
import com.example.icebutler_server.food.entity.FoodDeleteStatus;
import com.example.icebutler_server.fridge.dto.fridge.response.FridgeDiscardRes;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridge;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridgeUser;

import java.util.List;

public interface MultiFridgeFoodCustom  {
    Long findByDeleteCategoryForStatistics(FoodDeleteStatus deleteCategory, MultiFridge multiFridge, FoodCategory category, Integer year, Integer month);
    FridgeDiscardRes findByMultiFridgeForDisCardFood(MultiFridge fridge);
    List<Food> findByUserForMultiFridgeRecipeFoodList(MultiFridge fridge);
    void deleteOwnerByMultiFridgeUser(MultiFridgeUser multiFridgeUser);
}
