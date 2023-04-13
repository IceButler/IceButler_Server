package com.example.icebutler_server.fridge.service;

import com.example.icebutler_server.food.dto.assembler.FoodAssembler;
import com.example.icebutler_server.food.repository.FoodRepository;
import com.example.icebutler_server.food.entity.FoodCategory;
import com.example.icebutler_server.fridge.dto.fridge.response.GetFridgesMainRes;
import com.example.icebutler_server.fridge.dto.fridge.assembler.*;
import com.example.icebutler_server.fridge.dto.fridge.response.*;
import com.example.icebutler_server.fridge.dto.fridge.request.*;
import com.example.icebutler_server.fridge.repository.fridge.*;
import com.example.icebutler_server.fridge.exception.*;
import com.example.icebutler_server.fridge.entity.fridge.*;
import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.fridge.repository.multiFridge.MultiFridgeUserRepository;
import com.example.icebutler_server.global.dto.response.ResponseCustom;
import com.example.icebutler_server.global.entity.FridgeRole;
import com.example.icebutler_server.user.exception.UserNotFoundException;
import com.example.icebutler_server.user.entity.User;
import com.example.icebutler_server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class FridgeServiceImpl implements FridgeService {

  private final FridgeRepository fridgeRepository;
  private final FridgeUserRepository fridgeUserRepository;
  private final MultiFridgeUserRepository multiFridgeUserRepository;
  private final UserRepository userRepository;
  private final FridgeFoodRepository fridgeFoodRepository;
  private final FoodRepository foodRepository;

  private final FridgeAssembler fridgeAssembler;
  private final FridgeFoodAssembler fridgeFoodAssembler;
  private final FoodAssembler foodAssembler;

  @Transactional
  public Long registerFridge(FridgeRegisterReq registerFridgeReq) {
    if (!StringUtils.hasText(registerFridgeReq.getFridgeName())) throw new FridgeNameEmptyException();
    Fridge fridge = fridgeAssembler.toEntity(registerFridgeReq);
    fridgeRepository.save(fridge);

    List<FridgeUser> fridgeUsers = new ArrayList<>();
    List<User> users = registerFridgeReq.getMembers().stream().map(m -> userRepository.findByUserIdxAndIsEnable(m.getUserIdx(), true).orElseThrow(UserNotFoundException::new)).collect(Collectors.toList());
    User owner = userRepository.findByUserIdxAndIsEnable(registerFridgeReq.getOwner(), true).orElseThrow(UserNotFoundException::new);

    // fridge - fridgeUser  연관관계 추가
    for (User user : users) {
      fridgeUsers.add(FridgeUser.builder().fridge(fridge).user(user).role(FridgeRole.MEMBER).build());
    }
    fridgeUsers.add(FridgeUser.builder().fridge(fridge).user(owner).role(FridgeRole.OWNER).build());

    fridgeUserRepository.saveAll(fridgeUsers);

    return fridge.getFridgeIdx();
  }

  public FridgeMainRes getFoods(Long fridgeIdx, Long userIdx, String category) {
    User user = this.userRepository.findByUserIdxAndIsEnable(userIdx, true).orElseThrow(UserNotFoundException::new);
    Fridge fridge = this.fridgeRepository.findByFridgeIdxAndIsEnable(fridgeIdx, true).orElseThrow(FridgeNotFoundException::new);

    if (category == null) {
      // 값이 없으면 전체 조회
      return FridgeMainRes.toFridgeDto(this.fridgeFoodRepository.findByIsEnableOrderByShelfLife(true));
    } else {
      // 값이 있으면 특정 값을 불러온 조회
      return FridgeMainRes.toFridgeDto(this.fridgeFoodRepository.findByFood_FoodCategoryAndIsEnableOrderByShelfLife(FoodCategory.getFoodCategoryByName(category), true));
    }
  }

  @Transactional
  public void modifyFridge(Long fridgeIdx, FridgeModifyReq updateFridgeReq, Long userIdx) {
    User user = this.userRepository.findByUserIdxAndIsEnable(userIdx, true).orElseThrow(UserNotFoundException::new);
    Fridge fridge = this.fridgeRepository.findByFridgeIdxAndIsEnable(fridgeIdx, true).orElseThrow(FridgeNotFoundException::new);
    FridgeUser owner = this.fridgeUserRepository.findByFridgeAndUserAndRoleAndIsEnable(fridge, user, FridgeRole.OWNER, true).orElseThrow(InvalidFridgeUserRoleException::new);

    // 오너 업데이트
    if (owner.getUser().getUserIdx() != updateFridgeReq.getNewOwnerIdx()) {
      FridgeUser newOwner = this.fridgeUserRepository.findByFridgeAndUser_UserIdxAndRoleAndIsEnableAndUser_IsEnable(fridge, updateFridgeReq.getNewOwnerIdx(), FridgeRole.MEMBER, true, true).orElseThrow(FridgeUserNotFoundException::new);
      this.fridgeAssembler.toUpdateFridgeOwner(owner, newOwner);
    }

    // 냉장고 정보 (이름, 설명) 업데이트
    if (!StringUtils.hasText(updateFridgeReq.getFridgeName())) throw new FridgeNameEmptyException();
    this.fridgeAssembler.toUpdateBasicMultiFridgeInfo(fridge, updateFridgeReq);

    // 멤버 업데이트
    if (updateFridgeReq.getMembers() != null) {
      List<FridgeUser> members = this.fridgeUserRepository.findByFridgeAndIsEnable(fridge, true);
      List<User> newMembers = updateFridgeReq.getMembers().stream()
              .map(m -> this.userRepository.findByUserIdxAndIsEnable(m.getUserIdx(), true).orElseThrow(UserNotFoundException::new)).collect(Collectors.toList());
      List<FridgeUser> checkNewMember = this.fridgeAssembler.toUpdateFridgeMembers(newMembers, members);

      if (!checkNewMember.isEmpty()) {
        this.fridgeUserRepository.saveAll(checkNewMember);
      }
    }
  }

  @Transactional
  public ResponseCustom<Long> removeFridge(Long fridgeIdx, Long userId) {
    User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    Fridge fridge = fridgeRepository.findById(fridgeIdx).orElseThrow(FridgeNotFoundException::new);
    FridgeUser fridgeUser = (FridgeUser) fridgeUserRepository.findByUserAndFridgeAndIsEnable(user, fridge, true).orElseThrow(FridgeUserNotFoundException::new);

    // todo 냉장고 자체 삭제, 멤버가 속해있는 냉장고 삭제
    if (fridgeUser.getRole() != FridgeRole.OWNER) fridgeUser.updateFridgeUser(null, user, null);

    return ResponseCustom.OK(fridge.getFridgeIdx());
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
    fridgeUserRepository.findByUserAndFridgeAndIsEnable(user, fridge, true).orElseThrow(FridgeUserNotFoundException::new);

    User owner = userRepository.findById(fridgeFoodReq.getOwnerIdx()).orElseThrow(UserNotFoundException::new);
    fridgeUserRepository.findByUserAndFridgeAndIsEnable(owner, fridge, true).orElseThrow(FridgeUserNotFoundException::new);
    Food food = foodRepository.findByFoodName(fridgeFoodReq.getFoodName());

    if (food == null) food = foodRepository.save(foodAssembler.toEntity(fridgeFoodReq));
    fridgeFoodRepository.save(fridgeFoodAssembler.toEntity(owner, fridge, food, fridgeFoodReq));
  }


  public void modifyFridgeFood(Long fridgeIdx, Long fridgeFoodIdx, FridgeFoodReq fridgeFoodReq, Long userIdx) {
    User user = this.userRepository.findByUserIdxAndIsEnable(userIdx, true)
            .orElseThrow(UserNotFoundException::new);
    Fridge fridge = this.fridgeRepository.findByFridgeIdxAndIsEnable(fridgeIdx, true)
            .orElseThrow(FridgeNotFoundException::new);
    this.fridgeUserRepository.findByFridgeAndUserAndIsEnable(fridge, user, true)
            .orElseThrow(FridgeUserNotFoundException::new);
    FridgeFood modifyFridgeFood = this.fridgeFoodRepository.findByFridgeFoodIdxAndOwnerAndFridgeAndIsEnable(fridgeFoodIdx, user, fridge, true)
            .orElseThrow(FridgeFoodNotFoundException::new);

    if(!modifyFridgeFood.getFood().getFoodName().equals(fridgeFoodReq.getFoodName())) {
      Food food = this.foodRepository.findByFoodName(fridgeFoodReq.getFoodName());
      if(food == null) food = foodRepository.save(this.foodAssembler.toEntity(fridgeFoodReq));
      this.fridgeFoodAssembler.toUpdateFridgeFoodInfo(modifyFridgeFood, food);
    }

    this.fridgeFoodAssembler.toUpdateBasicFridgeFoodInfo(modifyFridgeFood, fridgeFoodReq);

    if(!modifyFridgeFood.getOwner().getUserIdx().equals(fridgeFoodReq.getOwnerIdx())){
      User newOwner = this.userRepository.findByUserIdxAndIsEnable(fridgeFoodReq.getOwnerIdx(), true)
              .orElseThrow(UserNotFoundException::new);
      this.fridgeUserRepository.findByFridgeAndUserAndIsEnable(fridge, newOwner, true)
              .orElseThrow(FridgeUserNotFoundException::new);
      this.fridgeFoodAssembler.toUpdateFridgeFoodOwner(modifyFridgeFood, newOwner);
    }
  }

  @Override
  //냉장고 내 유저 조회
  public FridgeUserMainRes searchMembers(Long fridgeIdx,Long userIdx){
    Fridge fridge = fridgeRepository.findById(fridgeIdx).orElseThrow(FridgeNotFoundException::new);

    return new FridgeUserMainRes(this.fridgeUserRepository.findByFridgeAndIsEnable(fridge, true).stream()
            .map(ff -> new FridgeUsersRes(ff.getUser().getUserIdx(), ff.getUser().getNickname(), ff.getUser().getProfileImage())).collect(Collectors.toList()));
  }

  @Override
  public FridgeFoodsStatistics getFridgeFoodStatistics(Long multiFridgeIdx, String deleteCategory, Long userIdx, Integer year, Integer month) {
    return null;
  }

  //냉장고 선택 화면 전체 조회
  public GetFridgesMainRes getFridges(Long fridgeIdx, Long userIdx) {
    User user = userRepository.findByUserIdxAndIsEnable(userIdx, true).orElseThrow(UserNotFoundException::new);
    return GetFridgesMainRes.toDto(fridgeUserRepository.findByUserAndIsEnable(user, true), multiFridgeUserRepository.findByUserAndIsEnable(user, true));
  }
}
