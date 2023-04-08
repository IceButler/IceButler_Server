package com.example.icebutler_server.food.service;

import com.example.icebutler_server.food.dto.response.FoodRes;
import com.example.icebutler_server.food.entity.FoodCategory;
import com.example.icebutler_server.food.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class FoodServiceImpl implements FoodService{

    private final FoodRepository foodRepository;

    @Override
    public List<FoodRes> getAllFood() {
        return foodRepository.findAll().stream().map(FoodRes::toDto).collect(Collectors.toList());
    }

    @Override
    public List<FoodRes> getAllFoodByCategory(String foodCategoryName) {
        FoodCategory foodCategory = FoodCategory.getFoodCategoryByName(foodCategoryName);
        return foodRepository.findAllByFoodCategory(foodCategory)
                .stream().map(FoodRes::toDto).collect(Collectors.toList());
    }
}
