package com.example.icebutler_server.fridge.service;

import com.example.icebutler_server.fridge.dto.fridge.request.FridgeModifyReq;
import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.global.dto.response.ResponseCustom;

import java.util.List;

public interface FridgeService {
  public void modifyFridge(Long fridgeIdx, FridgeModifyReq updateFridgeReq, Long userIdx);

  public ResponseCustom<Long> removeFridge(Long fridgeIdx, Long userIdx);


  public List<Food> findFoodByName(Long fridgeIdx, Long ownerIdx, String foodName);

  }
