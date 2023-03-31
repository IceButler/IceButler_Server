package com.example.icebutler_server.fridge.service;

import com.example.icebutler_server.fridge.Repository.FridgeRepository;
import com.example.icebutler_server.fridge.dto.FridgeAssembler;
import com.example.icebutler_server.fridge.dto.request.CreateFridgeReq;
import com.example.icebutler_server.fridge.dto.request.UpdateFridgeReq;
import com.example.icebutler_server.fridge.dto.response.FridgeFoodsRes;
import com.example.icebutler_server.fridge.dto.response.FridgeRes;
import com.example.icebutler_server.fridge.entity.Food;
import com.example.icebutler_server.fridge.entity.Fridge;
import com.example.icebutler_server.global.dto.response.ResponseCustom;
import com.example.icebutler_server.global.exception.BaseException;
import com.example.icebutler_server.user.entity.User;
import com.example.icebutler_server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;

import static com.example.icebutler_server.global.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class FridgeServiceImpl implements FridgeService {

  private final FridgeRepository fridgeRepository;
  private final UserRepository userRepository;

  private final FridgeAssembler fridgeAssembler;

  @Transactional
  public ResponseCustom<FridgeRes> createFridge(CreateFridgeReq createFridgeReq) throws BaseException {
    User user = userRepository.findById(createFridgeReq.getOwner()).orElseThrow(() -> new BaseException(NULL_FRIDGE_IDX));

    if (fridgeAssembler.isEmptyFridgeName(createFridgeReq)) throw new BaseException(NULL_FRIDGE_NAME);
    Fridge fridge = fridgeAssembler.toEntity(createFridgeReq, user);
    fridgeRepository.save(fridge);

    return ResponseCustom.CREATED(fridgeAssembler.toDto(fridge));
  }

  @Transactional
  public ResponseCustom<?> updateFridge(UpdateFridgeReq updateFridgeReq, Long userId) throws BaseException {
    Fridge fridge = fridgeRepository.findById(updateFridgeReq.getFridgeId()).orElseThrow();

    // todo 로그인 설정 이후 userId refactor
    fridge.updateOwner(fridgeAssembler.updateFridgeOwner(updateFridgeReq, userId));
    fridge.updateMembers(fridgeAssembler.updateMembers(updateFridgeReq));
    fridge.updateNameAndComment(fridgeAssembler.toUpdateEntity(updateFridgeReq));

    return ResponseCustom.OK(SUCCESS);
  }

  @Transactional
  public ResponseCustom<Long> deleteFridge(Long fridgeId) throws BaseException {
    Fridge fridge = fridgeRepository.findById(fridgeId).orElseThrow(() -> new BaseException(NULL_FRIDGE_IDX));
    fridge.updateIsEnable(false);

    return ResponseCustom.OK(fridge.getFridgeIdx());
  }

  @Transactional
  public FridgeFoodsRes getFoods(Long ownerId, Long fridgeId) throws BaseException {
    return fridgeAssembler.getFridgeFoods(ownerId, fridgeId);
  }

  @Transactional
  public List<Food> findFoodByName(Long fridgeId, Long ownerId, String foodName) throws BaseException {
    return fridgeAssembler.findFoodByFoodName(fridgeId, ownerId, foodName);
  }
}
