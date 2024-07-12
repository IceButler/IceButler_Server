package com.example.icebutler_server.fridge.service;

import com.example.icebutler_server.fridge.dto.request.*;
import com.example.icebutler_server.fridge.dto.response.*;

import java.io.IOException;
import java.util.List;

public interface FridgeService {
  FridgeMainRes getFoods(Long fridgeId, Long userId, String category);
  Long registerFridge(FridgeRegisterReq registerFridgeReq, Long ownerId);
  void modifyFridge(Long fridgeId, FridgeModifyReq updateFridgeReq, Long userId);
  Long removeFridge(Long fridgeId, Long userId);
  Long removeFridgeUser(Long fridgeId, Long userId) throws IOException;
  List<FridgeFoodsRes> searchFridgeFood(Long fridgeId, Long ownerId, String foodName);
  FridgeFoodRes getFridgeFood(Long fridgeId, Long fridgeFoodId, Long userId);
  void addFridgeFood(FridgeFoodsReq fridgeFoodsReq, Long fridgeId, Long userId);
  void modifyFridgeFood(Long fridgeId, Long fridgeFoodId, FridgeFoodReq fridgeFoodReq, Long userId);
  void deleteFridgeFood(DeleteFridgeFoodsReq deleteFridgeFoodsReq, String deleteType, Long fridgeId, Long userId);
  FridgeUserMainRes searchMembers(Long fridgeId, Long userId);
  FridgeFoodsStatistics getFridgeFoodStatistics(Long fridgeId, String deleteCategory, Long userId, Integer year, Integer month);
  RecipeFridgeFoodListsRes getFridgeUserFoodList(Long fridgeId, Long userId);
  void notifyFridgeFood();
}
