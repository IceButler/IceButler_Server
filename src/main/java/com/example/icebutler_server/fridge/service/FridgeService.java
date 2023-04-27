package com.example.icebutler_server.fridge.service;

import com.example.icebutler_server.fridge.dto.fridge.request.FridgeFoodReq;
import com.example.icebutler_server.fridge.dto.fridge.request.FridgeFoodsReq;
import com.example.icebutler_server.fridge.dto.fridge.request.FridgeModifyReq;
import com.example.icebutler_server.fridge.dto.fridge.response.*;

public interface FridgeService {

  FridgeMainRes getFoods(Long fridgeIdx, Long userIdx, String category);
  void modifyFridge(Long fridgeIdx, FridgeModifyReq updateFridgeReq, Long userIdx);
  Long removeFridge(Long fridgeIdx, Long userIdx);
  SearchFridgeFoodRes searchFridgeFood(Long fridgeIdx, Long ownerIdx, String foodName);
  FridgeFoodRes getFridgeFood(Long fridgeIdx, Long fridgeFoodIdx, Long userIdx);
  void addFridgeFood(FridgeFoodsReq fridgeFoodsReq, Long fridgeIdx, Long userIdx);
  void modifyFridgeFood(Long fridgeIdx, Long fridgeFoodIdx, FridgeFoodReq fridgeFoodReq, Long userIdx);
  FridgeUserMainRes searchMembers(Long fridgeIdx,Long userIdx);
  FridgeFoodsStatistics getFridgeFoodStatistics(Long multiFridgeIdx, String deleteCategory, Long userIdx, Integer year, Integer month);


}
