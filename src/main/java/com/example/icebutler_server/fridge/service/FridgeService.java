package com.example.icebutler_server.fridge.service;

import com.example.icebutler_server.fridge.dto.request.*;
import com.example.icebutler_server.fridge.dto.response.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface FridgeService {
  Long addFridge(AddFridgeReq registerFridgeReq, Long ownerId);
  void modifyFridge(Long fridgeId, EditFridgeReq updateFridgeReq, Long userId);
  void removeFridge(Long fridgeId, Long userId);
  void removeFridgeUser(Long fridgeId, Long userId);
  Page<FridgeFoodsRes> searchFridgeFoods(Long fridgeId, Long ownerId, String foodName, String category, Pageable pageable);
  FridgeFoodRes getFridgeFood(Long fridgeId, Long fridgeFoodId, Long userId);
  void addFridgeFood(FridgeFoodsReq fridgeFoodsReq, Long fridgeId, Long userId);
  void modifyFridgeFood(Long fridgeId, Long fridgeFoodId, FridgeFoodReq fridgeFoodReq, Long userId);
  void deleteFridgeFood(DeleteFridgeFoodsReq deleteFridgeFoodsReq, String deleteType, Long fridgeId, Long userId);
  FridgeUserMainRes searchMembers(Long fridgeId, Long userId);
  FridgeFoodsStatistics getFridgeFoodStatistics(Long fridgeId, String deleteCategory, Long userId, Integer year, Integer month);
  RecipeFridgeFoodListsRes getFridgeUserFoodList(Long fridgeId, Long userId);
  void notifyFridgeFood();
}
