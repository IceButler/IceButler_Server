package com.example.icebutler_server.fridge.service;

import com.example.icebutler_server.fridge.dto.request.FridgeRegisterReq;
import com.example.icebutler_server.fridge.dto.request.FridgeModifyReq;
import com.example.icebutler_server.fridge.dto.response.FridgeFoodsRes;
import com.example.icebutler_server.fridge.dto.response.FridgeRes;
import com.example.icebutler_server.fridge.entity.Food;
import com.example.icebutler_server.global.dto.response.ResponseCustom;
import com.example.icebutler_server.global.exception.BaseException;

import java.util.List;

public interface FridgeService {

  public ResponseCustom<FridgeRes> registerFridge(FridgeRegisterReq createFridgeReq)throws BaseException;

  public ResponseCustom<?> modifyFridge(FridgeModifyReq updateFridgeReq, Long userId) throws BaseException;

  public ResponseCustom<Long> removeFridge(Long fridgeId, Long userId) throws BaseException;

  public FridgeFoodsRes getFoods(Long fridgeId, Long userId) throws BaseException;

  public List<Food> findFoodByName(Long fridgeId, Long ownerId, String foodName) throws BaseException;

  }
