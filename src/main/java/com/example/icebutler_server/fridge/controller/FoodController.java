package com.example.icebutler_server.fridge.controller;

import com.example.icebutler_server.fridge.dto.response.FoodResponse;
import com.example.icebutler_server.fridge.service.FoodService;
import com.example.icebutler_server.global.dto.response.ResponseCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("foods")
@RestController
public class FoodController {

    private final FoodService foodService;
//    @GetMapping
//    public ResponseCustom<List<FoodResponse>> getAllFood() {
//        return foodService.getAllFood();
//    }

    @GetMapping
    public ResponseCustom<List<FoodResponse>> getAllFood(
            @RequestParam(defaultValue = "") String category)
    {
        return foodService.getAllFoodByCategory(category);
    }
}
