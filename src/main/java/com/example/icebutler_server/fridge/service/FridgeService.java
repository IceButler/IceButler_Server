package com.example.icebutler_server.fridge.service;

import com.example.icebutler_server.fridge.dto.request.CreateFridgeReq;
import com.example.icebutler_server.fridge.dto.request.UpdateFridgeReq;
import com.example.icebutler_server.fridge.dto.response.FridgeFoodsRes;
import com.example.icebutler_server.fridge.dto.response.FridgeRes;
import com.example.icebutler_server.fridge.entity.Food;
import com.example.icebutler_server.global.dto.response.ResponseCustom;
import com.example.icebutler_server.global.exception.BaseException;

import java.util.List;

public interface FridgeService {

  public ResponseCustom<FridgeRes> createFridge(CreateFridgeReq createFridgeReq)throws BaseException;

  public ResponseCustom<?> updateFridge(UpdateFridgeReq updateFridgeReq, Long userId) throws BaseException;

  public ResponseCustom<Long> deleteFridge(Long fridgeId) throws BaseException;

//  public FridgeFoodsRes getFoods(Long ownerId, Long fridgeId) throws BaseException;
//
//  public List<Food> findFoodByName(Long fridgeId, Long ownerId, String foodName) throws BaseException;

  }
