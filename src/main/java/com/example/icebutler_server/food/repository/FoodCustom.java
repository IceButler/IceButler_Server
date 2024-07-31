package com.example.icebutler_server.food.repository;

import com.example.icebutler_server.food.entity.Food;

import java.util.List;

public interface FoodCustom {
   List<Food> searchFood(String category, String word);
}
