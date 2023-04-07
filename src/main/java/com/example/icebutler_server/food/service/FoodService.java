package com.example.icebutler_server.food.service;

import com.example.icebutler_server.food.dto.response.FoodResponse;
import com.example.icebutler_server.global.dto.response.ResponseCustom;

import java.util.List;

public interface FoodService {
    ResponseCustom<List<FoodResponse>> getAllFood();

//    ResponseCustom<List<FoodResponse>> getAllFoodByCategory(String category);
}
