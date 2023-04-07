package com.example.icebutler_server.food.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("foods")
@RestController
public class FoodController {

//    private final FoodService foodService;
////    @GetMapping
////    public ResponseCustom<List<FoodResponse>> getAllFood() {
////        return foodService.getAllFood();
////    }
//
//    @GetMapping
//    public ResponseCustom<List<FoodResponse>> getAllFood(
//            @RequestParam(defaultValue = "") String category)
//    {
//        return foodService.getAllFoodByCategory(category);
//    }
}
