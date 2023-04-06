//package com.example.icebutler_server.fridge.service;
//
//import com.example.icebutler_server.fridge.dto.response.FoodResponse;
//import com.example.icebutler_server.food.entity.FoodCategory;
//import com.example.icebutler_server.fridge.repository.FoodCategoryRepository;
//import com.example.icebutler_server.food.repository.FoodRepository;
//import com.example.icebutler_server.global.dto.response.ResponseCustom;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RequiredArgsConstructor
//@Transactional(readOnly = true)
//@Service
//public class FoodServiceImpl implements FoodService{
//
//    private final FoodRepository foodRepository;
//    private final FoodCategoryRepository foodCategoryRepository;
//
//    @Override
//    public ResponseCustom<List<FoodResponse>> getAllFood() {
//        return ResponseCustom.OK(
//                foodRepository.findAll().stream().map(FoodResponse::toDto).collect(Collectors.toList())
//        );
//    }
//
//    @Override
//    public ResponseCustom<List<FoodResponse>> getAllFoodByCategory(String foodCategoryName) {
//        if(foodCategoryName.isEmpty()) return getAllFood();
//        FoodCategory foodCategory = foodCategoryRepository.findByFoodCategory(foodCategoryName);
//        return ResponseCustom.OK(
//                foodRepository.findAllByFoodCategory(foodCategory).stream().map(FoodResponse::toDto).collect(Collectors.toList())
//        );
//    }
//}
