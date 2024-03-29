package com.example.icebutler_server.food.service;

import com.example.icebutler_server.food.dto.request.FoodReq;
import com.example.icebutler_server.food.dto.response.BarcodeFoodRes;
import com.example.icebutler_server.food.dto.response.FoodRes;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

public interface FoodService {
    void addFood(FoodReq foodReq);
    List<FoodRes> getAllFood();
    List<FoodRes> getAllFoodByCategory(String categoryName);

    BarcodeFoodRes searchByBarcode(String barcodeNum) throws IOException, ParseException, org.json.simple.parser.ParseException;

    List<FoodRes> getAllFoodByCategoryAndWord(String categoryName, String word);
    List<FoodRes> getAllFoodByWord(String word);
}
