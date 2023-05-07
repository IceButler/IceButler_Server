package com.example.icebutler_server.fridge.service;

import com.example.icebutler_server.food.dto.assembler.FoodAssembler;
import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.food.entity.FoodCategory;
import com.example.icebutler_server.food.entity.FoodDeleteStatus;
import com.example.icebutler_server.food.repository.FoodRepository;
import com.example.icebutler_server.fridge.dto.fridge.assembler.FridgeAssembler;
import com.example.icebutler_server.fridge.dto.fridge.assembler.FridgeFoodAssembler;
import com.example.icebutler_server.fridge.dto.fridge.request.*;
import com.example.icebutler_server.fridge.dto.fridge.response.*;
import com.example.icebutler_server.fridge.entity.fridge.Fridge;
import com.example.icebutler_server.fridge.entity.fridge.FridgeFood;
import com.example.icebutler_server.fridge.entity.fridge.FridgeUser;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridge;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridgeUser;
import com.example.icebutler_server.fridge.exception.*;
import com.example.icebutler_server.fridge.repository.fridge.FridgeFood.FridgeFoodRepository;
import com.example.icebutler_server.fridge.repository.fridge.FridgeRepository;
import com.example.icebutler_server.fridge.repository.fridge.FridgeUserRepository;
import com.example.icebutler_server.fridge.repository.multiFridge.MultiFridgeRepository;
import com.example.icebutler_server.fridge.repository.multiFridge.MultiFridgeUserRepository;
import com.example.icebutler_server.global.entity.FridgeRole;
import com.example.icebutler_server.global.sqs.AmazonSQSSender;
import com.example.icebutler_server.global.sqs.FoodData;
import com.example.icebutler_server.user.entity.User;
import com.example.icebutler_server.user.exception.UserNotFoundException;
import com.example.icebutler_server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class FridgeServiceImpl implements FridgeService {

  private final FridgeRepository fridgeRepository;
  private final FridgeUserRepository fridgeUserRepository;
  private final MultiFridgeRepository multiFridgeRepository;
  private final MultiFridgeUserRepository multiFridgeUserRepository;
  private final UserRepository userRepository;
  private final FridgeFoodRepository fridgeFoodRepository;
  private final FoodRepository foodRepository;

  private final FridgeAssembler fridgeAssembler;
  private final FridgeFoodAssembler fridgeFoodAssembler;
  private final FoodAssembler foodAssembler;

  private final AmazonSQSSender amazonSQSSender;

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

  @Override
  public FridgeMainRes getFoods(Long fridgeIdx, Long userIdx, String category) {
    User user = this.userRepository.findByUserIdxAndIsEnable(userIdx, true).orElseThrow(UserNotFoundException::new);
    Fridge fridge = this.fridgeRepository.findByFridgeIdxAndIsEnable(fridgeIdx, true).orElseThrow(FridgeNotFoundException::new);

    if (category == null) {
      // 값이 없으면 전체 조회
      return FridgeMainRes.toFridgeDto(this.fridgeFoodRepository.findByFridgeForDisCardFood(fridge), this.fridgeFoodRepository.findByFridgeAndIsEnableOrderByShelfLife(fridge, true));
    } else {
      // 값이 있으면 특정 값을 불러온 조회
      return FridgeMainRes.toFridgeDto(this.fridgeFoodRepository.findByFridgeForDisCardFood(fridge),this.fridgeFoodRepository.findByFridgeAndFood_FoodCategoryAndIsEnableOrderByShelfLife(fridge, FoodCategory.getFoodCategoryByName(category), true));
    }
  }

  @Override
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

  // 냉장고 자체 삭제
  @Transactional
  public Long removeFridge(Long fridgeIdx, Long userId) {
    User user = userRepository.findByUserIdxAndIsEnable(userId, true).orElseThrow(UserNotFoundException::new);
    Fridge fridge = fridgeRepository.findByFridgeIdxAndIsEnable(fridgeIdx, true).orElseThrow(FridgeNotFoundException::new);
    FridgeUser owner = (FridgeUser) fridgeUserRepository.findByUserAndFridgeAndIsEnable(user, fridge, true).orElseThrow(FridgeUserNotFoundException::new);
    List<FridgeUser> fridgeUsers = fridgeUserRepository.findByFridgeAndIsEnable(fridge, true);
    List<FridgeFood> fridgeFoods = fridgeFoodRepository.findByFridgeAndIsEnableOrderByShelfLife(fridge, true);

    fridgeAssembler.removeFridge(owner, fridge, fridgeUsers, fridgeFoods);
    fridgeFoodRepository.removeFridgeFoodByFridge(false, fridge);

    return fridge.getFridgeIdx();
  }

  // 냉장고 개별 삭제
  @Transactional
  public Long removeFridgeUser(Long fridgeIdx, Long userIdx) {
    User user = userRepository.findByUserIdxAndIsEnable(userIdx, true).orElseThrow(UserNotFoundException::new);
    Fridge fridge = fridgeRepository.findByFridgeIdxAndIsEnable(fridgeIdx, true).orElseThrow(FridgeNotFoundException::new);
    FridgeUser fridgeUser = (FridgeUser) fridgeUserRepository.findByUserAndFridgeAndIsEnable(user, fridge, true).orElseThrow(FridgeUserNotFoundException::new);

    if(fridgeUser.getRole() == FridgeRole.OWNER) throw new PermissionDeniedException();
    fridgeUser.remove();

    return fridge.getFridgeIdx();
  }

  @Override
  public SearchFridgeFoodRes searchFridgeFood(Long fridgeIdx, Long userIdx, String keyword) {
    Fridge fridge = fridgeRepository.findByFridgeIdxAndIsEnable(fridgeIdx, true).orElseThrow(FridgeNotFoundException::new);
    List<FridgeFood> searchFoods = fridgeFoodRepository.findByFoodDetailNameContainingAndFridgeAndIsEnable(keyword, fridge, true);
    return SearchFridgeFoodRes.toDto(searchFoods, fridgeIdx, userIdx);
  }

  @Override
  public FridgeFoodRes getFridgeFood(Long fridgeIdx, Long fridgeFoodIdx, Long userIdx) {
    User user = userRepository.findByUserIdxAndIsEnable(userIdx, true).orElseThrow(UserNotFoundException::new);
    Fridge fridge = fridgeRepository.findByFridgeIdxAndIsEnable(fridgeIdx, true).orElseThrow(FridgeNotFoundException::new);
    fridgeUserRepository.findByUserAndFridgeAndIsEnable(user, fridge, true).orElseThrow(FridgeUserNotFoundException::new);
    FridgeFood fridgeFood = fridgeFoodRepository.findByFridgeFoodIdxAndFridgeAndIsEnable(fridgeFoodIdx, fridge, true).orElseThrow(FridgeFoodNotFoundException::new);

    return fridgeFoodAssembler.getFridgeFood(fridgeFood);
  }

  @Override
  @Transactional
  public void addFridgeFood(FridgeFoodsReq fridgeFoodsReq, Long fridgeIdx, Long userIdx) {
    User user = userRepository.findByUserIdxAndIsEnable(userIdx, true).orElseThrow(UserNotFoundException::new);
    Fridge fridge = fridgeRepository.findByFridgeIdxAndIsEnable(fridgeIdx, true).orElseThrow(FridgeNotFoundException::new);
    fridgeUserRepository.findByUserAndFridgeAndIsEnable(user, fridge, true).orElseThrow(FridgeUserNotFoundException::new);

    List<FridgeFood> fridgeFoods = new ArrayList<>();
    for (FridgeFoodReq fridgeFoodReq : fridgeFoodsReq.getFridgeFoods()){
      User owner = null;
      if(fridgeFoodReq.getOwnerIdx() != null) {
        owner = userRepository.findByUserIdxAndIsEnable(fridgeFoodReq.getOwnerIdx(), true).orElseThrow(UserNotFoundException::new);
        fridgeUserRepository.findByUserAndFridgeAndIsEnable(owner, fridge, true).orElseThrow(FridgeUserNotFoundException::new);
      }
      Food food = foodRepository.findByFoodName(fridgeFoodReq.getFoodName())
              .orElseGet(()->{
                Food save = foodRepository.save(foodAssembler.toEntity(fridgeFoodReq));
                amazonSQSSender.sendMessage(FoodData.toDto(save));
                return save;
              });

      fridgeFoods.add(fridgeFoodAssembler.toEntity(owner, fridge, food, fridgeFoodReq));
    }
    fridgeFoodRepository.saveAll(fridgeFoods);
  }

  @Override
  @Transactional
  public void modifyFridgeFood(Long fridgeIdx, Long fridgeFoodIdx, FridgeFoodReq fridgeFoodReq, Long userIdx) {
    User user = this.userRepository.findByUserIdxAndIsEnable(userIdx, true)
            .orElseThrow(UserNotFoundException::new);
    Fridge fridge = this.fridgeRepository.findByFridgeIdxAndIsEnable(fridgeIdx, true)
            .orElseThrow(FridgeNotFoundException::new);
    this.fridgeUserRepository.findByFridgeAndUserAndIsEnable(fridge, user, true)
            .orElseThrow(FridgeUserNotFoundException::new);
    FridgeFood modifyFridgeFood = this.fridgeFoodRepository.findByFridgeFoodIdxAndFridgeAndIsEnable(fridgeFoodIdx, fridge, true)
            .orElseThrow(FridgeFoodNotFoundException::new);

    if(!modifyFridgeFood.getFood().getFoodName().equals(fridgeFoodReq.getFoodName())) {
      Food food = this.foodRepository.findByFoodName(fridgeFoodReq.getFoodName())
              .orElseGet(()->foodRepository.save(this.foodAssembler.toEntity(fridgeFoodReq)));
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
  @Transactional
  public void deleteFridgeFood(DeleteFridgeFoodsReq deleteFridgeFoodsReq, String type, Long fridgeIdx, Long userIdx) {
    FoodDeleteStatus deleteStatus = FoodDeleteStatus.getFoodDeleteStatusByName(type);
    User user = this.userRepository.findByUserIdxAndIsEnable(userIdx, true)
            .orElseThrow(UserNotFoundException::new);
    Fridge fridge = this.fridgeRepository.findByFridgeIdxAndIsEnable(fridgeIdx, true)
            .orElseThrow(FridgeNotFoundException::new);
    this.fridgeUserRepository.findByFridgeAndUserAndIsEnable(fridge, user, true)
            .orElseThrow(FridgeUserNotFoundException::new);

    List<FridgeFood> deleteFridgeFoods = deleteFridgeFoodsReq.getDeleteFoods().stream()
            .map(foodIdx -> this.fridgeFoodRepository.findByFridgeFoodIdxAndFridgeAndIsEnable(foodIdx, fridge, true)
                    .orElseThrow(FridgeFoodNotFoundException::new))
            .collect(Collectors.toList());

    deleteFridgeFoods.forEach(food -> food.removeWithStatus(deleteStatus));
  }

  @Override
  //냉장고 내 유저 조회
  public FridgeUserMainRes searchMembers(Long fridgeIdx,Long userIdx){
    Fridge fridge = fridgeRepository.findByFridgeIdxAndIsEnable(fridgeIdx, true)
            .orElseThrow(FridgeNotFoundException::new);
    return FridgeUserMainRes.doDto(fridgeUserRepository.findByFridgeAndIsEnable(fridge,true));
  }

  @Override
  public FridgeFoodsStatistics getFridgeFoodStatistics(Long fridgeIdx, String deleteCategory, Long userIdx, Integer year, Integer month) {
    User user = this.userRepository.findByUserIdxAndIsEnable(userIdx,true).orElseThrow(UserNotFoundException::new);
    Fridge fridge = this.fridgeRepository.findByFridgeIdxAndIsEnable(fridgeIdx,true).orElseThrow(FridgeNotFoundException::new);
    this.fridgeUserRepository.findByFridgeAndUserAndIsEnable(fridge, user, true).orElseThrow(FridgeUserNotFoundException::new);

    Map<FoodCategory, Long> deleteStatusList = new HashMap<>();

    for(FoodCategory category: FoodCategory.values()){
      Long foodSize = this.fridgeFoodRepository.findByDeleteCategoryForStatistics(FoodDeleteStatus.getFoodDeleteStatusByName(deleteCategory), fridge, category, year, month);
      deleteStatusList.put(category, foodSize);
    }

    return this.fridgeFoodAssembler.toFoodStatisticsByDeleteStatus(deleteStatusList);
  }

  public SelectFridgesMainRes selectFridges(Long userIdx) {
    User user = userRepository.findByUserIdxAndIsEnable(userIdx, true).orElseThrow(UserNotFoundException::new);
    return SelectFridgesMainRes.toDto(fridgeUserRepository.findByUserAndIsEnable(user, true), multiFridgeUserRepository.findByUserAndIsEnable(user, true));
  }

  public GetFridgesMainRes myFridge(Long userIdx) {
    User user = userRepository.findByUserIdxAndIsEnable(userIdx, true).orElseThrow(UserNotFoundException::new);

    // 가정용 냉장고 조회
    List<FridgeUser> fridgeUsers = fridgeUserRepository.findByUserAndIsEnable(user, true);
    List<Fridge> fridges = fridgeUsers.stream().map(m -> fridgeRepository.findByFridgeIdxAndIsEnable(m.getFridge().getFridgeIdx(), true).orElseThrow(FridgeNotFoundException::new)).collect(Collectors.toList());
    List<List<FridgeUser>> fridgeUserListList = fridges.stream().map(m -> fridgeUserRepository.findByFridgeAndIsEnableOrderByRoleDesc(m, true)).collect(Collectors.toList());

    // 공용 냉장고 조회
    List<MultiFridgeUser> multiFridgeUsers = multiFridgeUserRepository.findByUserAndIsEnable(user, true);
    List<MultiFridge> multiFridges = multiFridgeUsers.stream().map(m -> multiFridgeRepository.findByMultiFridgeIdxAndIsEnable(m.getMultiFridge().getMultiFridgeIdx(), true).orElseThrow(FridgeNotFoundException::new)).collect(Collectors.toList());
    List<List<MultiFridgeUser>> multiFridgeUserListList = multiFridges.stream().map(m -> multiFridgeUserRepository.findByMultiFridgeAndIsEnableOrderByRoleDesc(m, true)).collect(Collectors.toList());

    return GetFridgesMainRes.toDto(fridgeUserListList, multiFridgeUserListList, userIdx);

  }

  //  사용자가 속한 가정용/공용 냉장고 food list
  public RecipeFridgeFoodListsRes getFridgeUserFoodList(Long fridgeIdx, Long multiFridgeIdx, Long userIdx) {
    User user = userRepository.findByUserIdxAndIsEnable(userIdx, true).orElseThrow(UserNotFoundException::new);

    if(fridgeIdx != null && multiFridgeIdx == null){
      Fridge fridge = this.fridgeRepository.findByFridgeIdxAndIsEnable(fridgeIdx,true).orElseThrow(FridgeNotFoundException::new);
      this.fridgeUserRepository.findByFridgeAndUserAndIsEnable(fridge, user, true).orElseThrow(FridgeUserNotFoundException::new);
      return RecipeFridgeFoodListsRes.toDto(this.fridgeFoodRepository.findByUserForFridgeRecipeFoodList(fridge));
    }else if(multiFridgeIdx != null && fridgeIdx == null) {
      MultiFridge fridge = this.multiFridgeRepository.findByMultiFridgeIdxAndIsEnable(multiFridgeIdx, true).orElseThrow(FridgeNotFoundException::new);
      this.multiFridgeUserRepository.findByMultiFridgeAndUserAndIsEnable(fridge, user, true).orElseThrow(FridgeUserNotFoundException::new);
      return RecipeFridgeFoodListsRes.toDto(this.fridgeFoodRepository.findByUserForMultiFridgeRecipeFoodList(fridge));
    }else return new RecipeFridgeFoodListsRes();
  }
}
