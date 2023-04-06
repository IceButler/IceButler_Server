package com.example.icebutler_server.fridge.service;

import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.fridge.dto.fridge.request.FridgeModifyReq;
import com.example.icebutler_server.fridge.dto.fridge.request.FridgeRegisterReq;
import com.example.icebutler_server.fridge.dto.fridge.response.FridgeFoodsRes;
import com.example.icebutler_server.fridge.dto.fridge.response.FridgeRes;
import com.example.icebutler_server.global.dto.response.ResponseCustom;
import com.example.icebutler_server.global.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class MultiFridgeServiceImpl implements FridgeService {
    @Override
    public ResponseCustom<FridgeRes> registerFridge(FridgeRegisterReq createFridgeReq) throws BaseException {
        return null;
    }

    @Override
    public ResponseCustom<?> modifyFridge(Long fridgeIdx, FridgeModifyReq updateFridgeReq, Long userIdx) throws BaseException {
        return null;
    }

    @Override
    public ResponseCustom<Long> removeFridge(Long fridgeIdx, Long userIdx) throws BaseException {
        return null;
    }

    @Override
    public FridgeFoodsRes getFoods(Long fridgeIdx, Long userIdx) throws BaseException {
        return null;
    }

    @Override
    public List<Food> findFoodByName(Long fridgeIdx, Long ownerIdx, String foodName) throws BaseException {
        return null;
    }
}
