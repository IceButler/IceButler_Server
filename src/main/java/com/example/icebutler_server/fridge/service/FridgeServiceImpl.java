package com.example.icebutler_server.fridge.service;

import com.example.icebutler_server.fridge.dto.response.FridgeFoodsRes;
import com.example.icebutler_server.fridge.entity.Food;
import com.example.icebutler_server.fridge.entity.FridgeUser;
import com.example.icebutler_server.fridge.exception.FridgeNameEmptyException;
import com.example.icebutler_server.fridge.exception.FridgeNotFoundException;
import com.example.icebutler_server.fridge.exception.UserNotFoundException;
import com.example.icebutler_server.fridge.repository.FridgeRepository;
import com.example.icebutler_server.fridge.dto.assembler.FridgeAssembler;
import com.example.icebutler_server.fridge.dto.request.FridgeRegisterReq;
import com.example.icebutler_server.fridge.dto.request.FridgeModifyReq;
import com.example.icebutler_server.fridge.dto.response.FridgeRes;
import com.example.icebutler_server.fridge.entity.Fridge;
import com.example.icebutler_server.fridge.repository.FridgeUserRepository;
import com.example.icebutler_server.global.dto.response.ResponseCustom;
import com.example.icebutler_server.user.entity.User;
import com.example.icebutler_server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;

import static com.example.icebutler_server.global.BaseResponseStatus.*;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class FridgeServiceImpl implements FridgeService {

  private final FridgeRepository fridgeRepository;
  private final FridgeUserRepository fridgeUserRepository;
  private final UserRepository userRepository;

  private final FridgeAssembler fridgeAssembler;

  @Transactional
  public ResponseCustom<FridgeRes> registerFridge(FridgeRegisterReq registerFridgeReq) {
    User user = userRepository.findById(registerFridgeReq.getOwner()).orElseThrow(UserNotFoundException::new);

    if (fridgeAssembler.isEmptyFridgeName(registerFridgeReq)) throw new FridgeNameEmptyException();
    Fridge fridge = fridgeAssembler.toEntity(registerFridgeReq, user);
    fridgeRepository.save(fridge);

    return ResponseCustom.CREATED(fridgeAssembler.toDto(fridge));
  }

  @Transactional
  public ResponseCustom<?> modifyFridge(Long fridgeIdx, FridgeModifyReq updateFridgeReq, Long userIdx) {
    Fridge fridge = fridgeRepository.findById(fridgeIdx).orElseThrow(FridgeNotFoundException::new);
    User originOwner = userRepository.findById(userIdx).orElseThrow(UserNotFoundException::new);
    User newOwner = userRepository.findByNickname(updateFridgeReq.getNewOwnerName());

    List<FridgeUser> fridgeUsers = new ArrayList<>();
    for (String name : updateFridgeReq.getUsersName()) {
      fridgeUsers.add(fridgeUserRepository.findByOwner(userRepository.findByNickname(name)));
    }

    // TODO: Fridge 엔티티 수정으로 인한 오류(수정해주세요..ㅎㅎ)
    fridge.updateOwner(fridgeAssembler.updateFridgeOwner(originOwner, newOwner));
    fridge.updateMembers(fridgeUsers);
    fridge.updateNameAndComment(fridgeAssembler.toUpdateEntity(updateFridgeReq));

    return ResponseCustom.OK(SUCCESS);
  }

  @Transactional
  public ResponseCustom<Long> removeFridge(Long fridgeIdx, Long userId) {
    User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    Fridge fridge = fridgeRepository.findByFridgeIdxAndOwner(fridgeIdx, user);

    if(fridge == null) throw new FridgeNotFoundException();
    fridge.setIsEnable(false);

    return ResponseCustom.OK(fridge.getFridgeIdx());
  }

  public FridgeFoodsRes getFoods(Long fridgeIdx, Long ownerIdx) {
    User owner = userRepository.findById(ownerIdx).orElseThrow(UserNotFoundException::new);
    Fridge fridge = fridgeRepository.findById(fridgeIdx).orElseThrow(FridgeNotFoundException::new);

    return fridgeAssembler.getFridgeFoods(owner, fridge);

  }

  @Transactional
  public List<Food> findFoodByName(Long fridgeIdx, Long ownerIdx, String foodName) {
    User owner = userRepository.findById(ownerIdx).orElseThrow(UserNotFoundException::new);
    Fridge fridge = fridgeRepository.findById(fridgeIdx).orElseThrow(FridgeNotFoundException::new);

    return fridgeAssembler.findFoodByFoodName(owner, fridge, foodName);
  }
}
