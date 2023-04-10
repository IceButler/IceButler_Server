package com.example.icebutler_server.fridge.service;

import com.example.icebutler_server.food.dto.assembler.FoodAssembler;
import com.example.icebutler_server.food.repository.FoodRepository;
import com.example.icebutler_server.food.entity.FoodCategory;
import com.example.icebutler_server.fridge.dto.fridge.assembler.*;
import com.example.icebutler_server.fridge.dto.fridge.response.*;
import com.example.icebutler_server.fridge.dto.fridge.request.*;
import com.example.icebutler_server.fridge.repository.fridge.*;
import com.example.icebutler_server.fridge.exception.*;
import com.example.icebutler_server.fridge.entity.fridge.*;
import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.global.dto.response.ResponseCustom;
import com.example.icebutler_server.user.exception.UserNotFoundException;
import com.example.icebutler_server.user.entity.User;
import com.example.icebutler_server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class FridgeServiceImpl implements FridgeService {

  private final FridgeRepository fridgeRepository;
  private final FridgeUserRepository fridgeUserRepository;
  private final UserRepository userRepository;
  private final FridgeFoodRepository fridgeFoodRepository;
  private final FoodRepository foodRepository;

  private final FridgeAssembler fridgeAssembler;
  private final FridgeFoodAssembler fridgeFoodAssembler;
  private final FoodAssembler foodAssembler;

  @Transactional
  public ResponseCustom<FridgeRes> registerFridge(FridgeRegisterReq registerFridgeReq) {
    User user = userRepository.findById(registerFridgeReq.getOwner()).orElseThrow(UserNotFoundException::new);

    if (fridgeAssembler.isEmptyFridgeName(registerFridgeReq)) throw new FridgeNameEmptyException();
    Fridge fridge = fridgeAssembler.toEntity(registerFridgeReq, user);
    fridgeRepository.save(fridge);

    return ResponseCustom.CREATED(fridgeAssembler.toDto(fridge));
  }

  public FridgeMainRes getFoods(Long fridgeIdx, Long userIdx, String category) {
    User user = this.userRepository.findByUserIdxAndIsEnable(userIdx, true).orElseThrow(UserNotFoundException::new);
    Fridge fridge = this.fridgeRepository.findByFridgeIdxAndIsEnable(fridgeIdx, true).orElseThrow(FridgeNotFoundException::new);

    if(category == null){
      // 값이 없으면 전체 조회
      return new FridgeMainRes(this.fridgeFoodRepository.findByIsEnableOrderByShelfLife(true).stream()
              .map(ff -> new FridgeFoodsRes(ff.getFridgeFoodIdx(), ff.getFood().getFoodName(), ff.getFood().getFoodIconName(), this.fridgeFoodAssembler.calShelfLife(ff.getShelfLife())))
              .collect(Collectors.toList()));
    }else{
      // 값이 있으면 특정 값을 불러온 조회
      return new FridgeMainRes(this.fridgeFoodRepository.findByFood_FoodCategoryAndIsEnableOrderByShelfLife(FoodCategory.getFoodCategoryByName(category), true).stream()
              .map(ff -> new FridgeFoodsRes(ff.getFridgeFoodIdx(), ff.getFood().getFoodName(), ff.getFood().getFoodIconName(), this.fridgeFoodAssembler.calShelfLife(ff.getShelfLife())))
              .collect(Collectors.toList()));

    }
  }

  @Transactional
  public void modifyFridge(Long fridgeIdx, FridgeModifyReq updateFridgeReq, Long userIdx) {
//    Fridge fridge = fridgeRepository.findById(fridgeIdx).orElseThrow(FridgeNotFoundException::new);
//    User originOwner = userRepository.findById(userIdx).orElseThrow(UserNotFoundException::new);
//    User newOwner = userRepository.findByNickname(updateFridgeReq.getNewOwnerName());
//
//    List<FridgeUser> fridgeUsers = new ArrayList<>();
//    for (String name : updateFridgeReq.getUsersName()) {
//      fridgeUsers.add(fridgeUserRepository.findByOwner(userRepository.findByNickname(name)));
//    }
//// TODO 엔티티 수정으로 인한 오류 해결 필요
////    fridge.updateOwner(fridgeAssembler.updateFridgeOwner(originOwner, newOwner));
//    fridge.updateMembers(fridgeUsers);
//    fridge.updateNameAndComment(fridgeAssembler.toUpdateEntity(updateFridgeReq));
//
//    return ResponseCustom.OK(SUCCESS);
  }

  @Transactional
  public ResponseCustom<Long> removeFridge(Long fridgeIdx, Long userId) {
//    User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
//    Fridge fridge = fridgeRepository.findByFridgeIdxAndOwner(fridgeIdx, user);
//
//    if(fridge == null) throw new FridgeNotFoundException();
//    fridge.setIsEnable(false);
//
//    return ResponseCustom.OK(fridge.getFridgeIdx());
    return null;
  }

  @Transactional
  public List<Food> findFoodByName(Long fridgeIdx, Long ownerIdx, String foodName) {
    User owner = userRepository.findById(ownerIdx).orElseThrow(UserNotFoundException::new);
    Fridge fridge = fridgeRepository.findById(fridgeIdx).orElseThrow(FridgeNotFoundException::new);

    return fridgeAssembler.findFoodByFoodName(owner, fridge, foodName);
  }

  @Override
  public FridgeFoodRes getFridgeFood(Long fridgeIdx, Long fridgeFoodIdx, Long userIdx) {
    userRepository.findById(userIdx).orElseThrow(UserNotFoundException::new);
    fridgeRepository.findById(fridgeIdx).orElseThrow(FridgeNotFoundException::new);
    FridgeFood fridgeFood = fridgeFoodRepository.findById(fridgeFoodIdx).orElseThrow(FridgeFoodNotFoundException::new);

    return fridgeFoodAssembler.getFridgeFood(fridgeFood);
  }

  @Override
  @Transactional
  public void addFridgeFood(FridgeFoodReq fridgeFoodReq, Long fridgeIdx, Long userIdx) {
    User user = userRepository.findById(userIdx).orElseThrow(UserNotFoundException::new);
    Fridge fridge = fridgeRepository.findById(fridgeIdx).orElseThrow(FridgeNotFoundException::new);
    fridgeUserRepository.findByUserAndFridge(user, fridge).orElseThrow(FridgeUserNotFoundException::new);

    User owner = userRepository.findById(fridgeFoodReq.getOwnerIdx()).orElseThrow(UserNotFoundException::new);
    fridgeUserRepository.findByUserAndFridge(owner, fridge).orElseThrow(FridgeUserNotFoundException::new);
    Food food = foodRepository.findByFoodName(fridgeFoodReq.getFoodName());

    if(food == null) food = foodRepository.save(foodAssembler.toEntity(fridgeFoodReq));
    fridgeFoodRepository.save(fridgeFoodAssembler.toEntity(owner, fridge, food, fridgeFoodReq));
  }

  public FridgeUserMainRes searchMembers(Long fridgeIdx, Long userIdx){
    User user=this.userRepository.findByUserIdxAndIsEnable(userIdx,true).orElseThrow(UserNotFoundException::new);
    Fridge fridge=this.fridgeRepository.findByFridgeIdxAndIsEnable(fridgeIdx,true).orElseThrow(FridgeNotFoundException::new);

    return new FridgeUserMainRes(this.fridgeUserRepository.findByFridge(fridge).stream()
            .map(ff -> new FridgeUsersRes(ff.getUser().getUserIdx(), ff.getUser().getNickname(),ff.getUser().getProfileImage())).collect(Collectors.toList()));
  }


}
