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

  public ResponseCustom<?> modifyFridge(Long fridgeIdx, FridgeModifyReq updateFridgeReq, Long userIdx) throws BaseException;

  public ResponseCustom<Long> removeFridge(Long fridgeIdx, Long userIdx) throws BaseException;

  public FridgeFoodsRes getFoods(Long fridgeIdx, Long userIdx) throws BaseException;

  public List<Food> findFoodByName(Long fridgeIdx, Long ownerIdx, String foodName) throws BaseException;

  }
