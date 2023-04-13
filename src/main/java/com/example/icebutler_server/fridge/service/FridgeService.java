package com.example.icebutler_server.fridge.service;

import com.example.icebutler_server.fridge.dto.fridge.request.FridgeFoodReq;
import com.example.icebutler_server.fridge.dto.fridge.request.FridgeModifyReq;
import com.example.icebutler_server.fridge.dto.fridge.response.FridgeFoodRes;
import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.fridge.dto.fridge.response.FridgeFoodsStatistics;
import com.example.icebutler_server.fridge.dto.fridge.response.FridgeMainRes;
import com.example.icebutler_server.fridge.dto.fridge.response.FridgeUserMainRes;
import com.example.icebutler_server.global.dto.response.ResponseCustom;

import java.util.List;

public interface FridgeService {

  FridgeMainRes getFoods(Long fridgeIdx, Long userIdx, String category);
  void modifyFridge(Long fridgeIdx, FridgeModifyReq updateFridgeReq, Long userIdx);
  ResponseCustom<Long> removeFridge(Long fridgeIdx, Long userIdx);
  List<Food> findFoodByName(Long fridgeIdx, Long ownerIdx, String foodName);
  FridgeFoodRes getFridgeFood(Long fridgeIdx, Long fridgeFoodIdx, Long userIdx);
  void addFridgeFood(FridgeFoodReq fridgeFoodReq, Long fridgeIdx, Long userIdx);
  void modifyFridgeFood(Long fridgeIdx, Long fridgeFoodIdx, FridgeFoodReq fridgeFoodReq, Long userIdx);
  FridgeUserMainRes searchMembers(Long fridgeIdx,Long userIdx);
  FridgeFoodsStatistics getFridgeFoodStatistics(Long multiFridgeIdx, String deleteCategory, Long userIdx, Integer year, Integer month);


}
