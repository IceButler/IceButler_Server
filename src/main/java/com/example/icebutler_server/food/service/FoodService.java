package com.example.icebutler_server.food.service;

import com.example.icebutler_server.food.dto.response.FoodRes;

import java.util.List;

public interface FoodService {
    List<FoodRes> getAllFood();
    List<FoodRes> getAllFoodByCategory(String category);
}
