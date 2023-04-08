package com.example.icebutler_server.food.service;

import com.example.icebutler_server.food.dto.response.FoodRes;
import org.apache.tomcat.util.json.ParseException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public interface FoodService {
    List<FoodRes> getAllFood();
    List<FoodRes> getAllFoodByCategory(String category);

    FoodRes searchByBarcode(String barcodeNum) throws IOException, ParseException, org.json.simple.parser.ParseException;
}
