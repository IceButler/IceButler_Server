package com.example.icebutler_server.food.controller;

import com.example.icebutler_server.food.service.FoodServiceImpl;
import com.example.icebutler_server.global.dto.response.ResponseCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/foods")
@RestController
public class FoodController {

    private final FoodServiceImpl foodService;

    // 식품 검색
    @GetMapping("")
    public ResponseCustom<?> searchFood(@RequestParam(required = false) String category, @RequestParam(required = false) String word) {
        if(category != null && word != null) return ResponseCustom.OK(foodService.getAllFoodByCategoryAndWord(category, word));
        else if(category != null) return ResponseCustom.OK(foodService.getAllFoodByCategory(category));
        else if(word != null) return ResponseCustom.OK(foodService.getAllFoodByWord(word));
        else return ResponseCustom.OK(foodService.getAllFood());
    }

    // 식품 바코드 검색
    @GetMapping("/barcode")
    public ResponseCustom<?> searchByBarcode(@RequestParam String code_num) throws IOException, org.json.simple.parser.ParseException {
        return ResponseCustom.OK(foodService.searchByBarcode(code_num));
    }
}
