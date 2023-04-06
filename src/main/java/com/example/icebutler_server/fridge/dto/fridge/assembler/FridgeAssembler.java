package com.example.icebutler_server.fridge.dto.fridge.assembler;

import com.example.icebutler_server.fridge.dto.fridge.response.FridgeFoodsRes;
import com.example.icebutler_server.fridge.dto.fridge.response.FridgeRes;
import com.example.icebutler_server.fridge.dto.fridge.response.FridgeUserRes;
import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.fridge.dto.fridge.request.FridgeRegisterReq;
import com.example.icebutler_server.fridge.dto.fridge.request.FridgeModifyReq;
import com.example.icebutler_server.fridge.entity.fridge.Fridge;
import com.example.icebutler_server.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FridgeAssembler {

  public Fridge toEntity(FridgeRegisterReq createFridgeReq, User user) {
    return Fridge.builder()
//            .owner(user)
            .fridgeName(createFridgeReq.getFridgeName())
            .fridgeComment(createFridgeReq.getFridgeComment())
            .fridgeUsers(createFridgeReq.getUsers())
            .build();
  }

  public FridgeRes toDto(Fridge fridge) {
    FridgeRes fridgeRes = new FridgeRes();
    // TODO 에러로 주석 처리
//    fridgeRes.setOwnerNickname(fridge.getOwner().getNickname());
//    fridgeRes.setFridgeName(fridge.getFridgeName());
//    fridgeRes.setComment(fridge.getFridgeComment());
//    fridgeRes.setUsers(fridge.getFridgeUsers().stream()
//            .map((fu) -> FridgeUserRes.toDto(fu.getOwner()))
//            .collect(Collectors.toList()));
    return fridgeRes;
  }

  public boolean isEmptyFridgeName(FridgeRegisterReq createFridgeReq) {
    return createFridgeReq.getFridgeName().isEmpty();
  }

  public Fridge toUpdateEntity(FridgeModifyReq updateFridgeReq) {
    return Fridge.builder()
            .fridgeName(updateFridgeReq.getFridgeName())
            .fridgeComment(updateFridgeReq.getFridgeComment())
            .build();
  }

  public User updateFridgeOwner(User originOwner, User newOwner) {
    if (newOwner != null) return newOwner;
    return originOwner;
  }

  public FridgeFoodsRes getFridgeFoods(User owner, Fridge fridge) {
//    List<Food> ownerFoods = owner.getFoods();
//    List<List<Food>> usersFoods = new ArrayList<>();
//
//    List<FridgeUser> fridgeUsers = fridge.getFridgeUsers();
//    for (FridgeUser users : fridgeUsers) {
//      usersFoods.add(users.getUser().getFoods());
//    }
//
//    return FridgeFoodsRes.builder()
//            .ownerFoods(ownerFoods)
//            .fridgeUserFoods(usersFoods)
//            .build();
    return null;
  }

  public List<Food> findFoodByFoodName(User owner, Fridge fridge, String foodName) {

//    List<Food> searchFood = new ArrayList<>();
//
//    //냉장고 오너가 등록한 음식 중 검색
//    List<Food> ownerFoods = owner.getFoods();
//    for (Food food : ownerFoods) {
//      if (food.getFoodName().equals(foodName)) searchFood.add(food);
//    }
//
//    //냉장고 멤버가 등록한 음식 중 검색
//    List<FridgeUser> fridgeUsers = fridge.getFridgeUsers();
//    for (FridgeUser fridgeUser : fridgeUsers) {
//      for (Food food : fridgeUser.getUser().getFoods()) {
//        if (food.getFoodName().equals(foodName)) searchFood.add(food);
//      }
//    }
////    검색결과 x -> 예외처리로 해야할지
////    if (searchFood.size() == 0) throw new BaseException(NULL_SEARCH_FOOD);
//    return searchFood;
    return null;
  }
}
