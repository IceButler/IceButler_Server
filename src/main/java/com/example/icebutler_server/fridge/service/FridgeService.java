package com.example.icebutler_server.fridge.service;

import com.example.icebutler_server.fridge.dto.fridge.request.*;
import com.example.icebutler_server.fridge.dto.fridge.response.*;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

public interface FridgeService {
  FridgeMainRes getFoods(Long fridgeIdx, Long userIdx, String category);
  Long registerFridge(FridgeRegisterReq registerFridgeReq, Long ownerIdx);
  void modifyFridge(Long fridgeIdx, FridgeModifyReq updateFridgeReq, Long userIdx);
  Long removeFridge(Long fridgeIdx, Long userIdx);
  Long removeFridgeUser(Long fridgeIdx, Long userIdx) throws IOException;
  SearchFridgeFoodRes searchFridgeFood(Long fridgeIdx, Long ownerIdx, String foodName);
  FridgeFoodRes getFridgeFood(Long fridgeIdx, Long fridgeFoodIdx, Long userIdx);
  void addFridgeFood(FridgeFoodsReq fridgeFoodsReq, Long fridgeIdx, Long userIdx);
  void modifyFridgeFood(Long fridgeIdx, Long fridgeFoodIdx, FridgeFoodReq fridgeFoodReq, Long userIdx);
  void deleteFridgeFood(DeleteFridgeFoodsReq deleteFridgeFoodsReq, String deleteType, Long fridgeIdx, Long userIdx);
  FridgeUserMainRes searchMembers(Long fridgeIdx,Long userIdx);
  FridgeFoodsStatistics getFridgeFoodStatistics(Long multiFridgeIdx, String deleteCategory, Long userIdx, Integer year, Integer month);
  RecipeFridgeFoodListsRes getFridgeUserFoodList(Long fridgeIdx, Long userIdx);
}
