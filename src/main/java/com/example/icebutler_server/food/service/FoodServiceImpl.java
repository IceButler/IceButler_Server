package com.example.icebutler_server.food.service;

import com.example.icebutler_server.food.dto.request.FoodReq;
import com.example.icebutler_server.food.dto.response.BarcodeFoodRes;
import com.example.icebutler_server.food.dto.response.FoodRes;
import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.food.repository.FoodRepository;
import com.example.icebutler_server.global.util.FoodBarcodeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class FoodServiceImpl implements FoodService {

    private final FoodRepository foodRepository;
    private final FoodBarcodeUtils foodBarcodeUtils;

    @Override
    public List<FoodRes> searchFood(String category, String word) {
        List<Food> searchFoods = foodRepository.searchFood(category, word);
        return searchFoods.stream().map(FoodRes::toDto).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void addFood(FoodReq foodReq) {
        this.foodRepository.save(Food.toEntity(foodReq));
    }

    @Override
    public BarcodeFoodRes searchByBarcode(String barcodeNum) {
        String foodDetailName = foodBarcodeUtils.callBarcodeApi(barcodeNum);
        return BarcodeFoodRes.toDto(foodDetailName);
    }

}
