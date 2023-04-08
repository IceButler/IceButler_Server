package com.example.icebutler_server.food.controller;

import com.example.icebutler_server.food.service.FoodServiceImpl;
import com.example.icebutler_server.global.dto.response.ResponseCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/foods")
@RestController
public class FoodController {

    private final FoodServiceImpl foodService;

    @GetMapping("")
    public ResponseCustom<?> getAllFood(@RequestParam(required = false) String category) {
        if(category==null) return ResponseCustom.OK(foodService.getAllFood());
        else return ResponseCustom.OK(foodService.getAllFoodByCategory(category));
    }
}
