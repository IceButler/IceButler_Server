package com.example.icebutler_server.fridge.service;

import com.example.icebutler_server.fridge.dto.fridge.request.FridgeRegisterReq;
import com.example.icebutler_server.fridge.dto.fridge.request.FridgeModifyReq;
import com.example.icebutler_server.fridge.dto.fridge.response.FridgeFoodsRes;
import com.example.icebutler_server.fridge.dto.fridge.response.FridgeRes;
import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.global.dto.response.ResponseCustom;
import com.example.icebutler_server.global.exception.BaseException;
import com.example.icebutler_server.user.entity.User;

import java.util.List;

public interface FridgeService {
  public void modifyFridge(Long fridgeIdx, FridgeModifyReq updateFridgeReq, Long userIdx);

  public ResponseCustom<Long> removeFridge(Long fridgeIdx, Long userIdx);

  public FridgeFoodsRes getFoods(Long fridgeIdx, Long userIdx);

  public List<Food> findFoodByName(Long fridgeIdx, Long ownerIdx, String foodName);
  List<User> searchMember(Long fridgeIdx,String nickName, Long ownerIdx);




  }
