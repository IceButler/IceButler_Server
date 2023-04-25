package com.example.icebutler_server.fridge.repository.fridge.FridgeFood;

import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.food.entity.FoodCategory;
import com.example.icebutler_server.food.entity.FoodDeleteStatus;
import com.example.icebutler_server.fridge.entity.fridge.Fridge;
import com.example.icebutler_server.user.entity.User;

import java.util.List;

public interface FridgeFoodCustom {
    Long findByDeleteCategoryForStatistics(FoodDeleteStatus deleteCategory, Fridge fridge, FoodCategory category, Integer year, Integer month);
    List<Food> findByUserForRecipeFridgeFoodList(User user);
}
