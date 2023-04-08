package com.example.icebutler_server.fridge.service;

import com.example.icebutler_server.fridge.dto.fridge.request.FridgeFoodReq;
import com.example.icebutler_server.fridge.dto.fridge.request.FridgeModifyReq;
import com.example.icebutler_server.fridge.dto.fridge.response.FridgeFoodRes;
import com.example.icebutler_server.fridge.dto.fridge.response.FridgeFoodsRes;
import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.global.dto.response.ResponseCustom;

import java.util.List;

public interface FridgeService {
  void modifyFridge(Long fridgeIdx, FridgeModifyReq updateFridgeReq, Long userIdx);
  ResponseCustom<Long> removeFridge(Long fridgeIdx, Long userIdx);
  FridgeFoodsRes getFoods(Long fridgeIdx, Long userIdx);
  List<Food> findFoodByName(Long fridgeIdx, Long ownerIdx, String foodName);
  FridgeFoodRes getFridgeFood(Long fridgeIdx, Long fridgeFoodIdx, Long userIdx);
  void addFridgeFood(FridgeFoodReq fridgeFoodReq, Long fridgeIdx, Long userIdx);
  }
