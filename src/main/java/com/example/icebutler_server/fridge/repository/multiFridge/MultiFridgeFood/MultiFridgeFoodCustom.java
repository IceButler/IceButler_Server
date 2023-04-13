package com.example.icebutler_server.fridge.repository.multiFridge.MultiFridgeFood;

import com.example.icebutler_server.food.entity.FoodCategory;
import com.example.icebutler_server.food.entity.FoodDeleteStatus;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridge;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridgeFood;

import java.util.List;

public interface MultiFridgeFoodCustom  {
    Long findByDeleteCategoryForStatistics(FoodDeleteStatus deleteCategory, MultiFridge multiFridge, FoodCategory category, Integer year, Integer month);
}
