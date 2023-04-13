package com.example.icebutler_server.fridge.dto.fridge.assembler;

import com.example.icebutler_server.fridge.dto.fridge.request.FridgeFoodReq;
import com.example.icebutler_server.fridge.dto.fridge.response.FridgeRes;
import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.fridge.dto.fridge.request.FridgeRegisterReq;
import com.example.icebutler_server.fridge.dto.fridge.request.FridgeModifyReq;
import com.example.icebutler_server.fridge.entity.fridge.Fridge;
import com.example.icebutler_server.fridge.entity.fridge.FridgeFood;
import com.example.icebutler_server.fridge.entity.fridge.FridgeUser;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridge;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridgeUser;
import com.example.icebutler_server.global.entity.FridgeRole;
import com.example.icebutler_server.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FridgeAssembler {

  public Fridge toEntity(FridgeRegisterReq createFridgeReq) {
    return Fridge.builder()
            .fridgeName(createFridgeReq.getFridgeName())
            .fridgeComment(createFridgeReq.getFridgeComment())
            .build();
  }

  public void toUpdateFridgeOwner(FridgeUser owner, FridgeUser newOwner) {
    owner.changeFridgeMember(owner.getUser());
    newOwner.changeFridgeOwner(newOwner.getUser());
  }

  public void toUpdateBasicMultiFridgeInfo(Fridge fridge, FridgeModifyReq updateFridgeReq) {
    fridge.updateBasicFridgeInfo(updateFridgeReq.getFridgeName(), updateFridgeReq.getFridgeComment());
  }

  public List<FridgeUser> toUpdateFridgeMembers(List<User> newMembers, List<FridgeUser> fridgeUsers) {
    for (FridgeUser member : fridgeUsers) {
      member.setIsEnable(false);
    }
    List<FridgeUser> checkNewMember = new ArrayList<>();

    for (User user : newMembers) {
      boolean hasMember = false;

      for (FridgeUser members : fridgeUsers) {
        if (user.equals(members.getUser()) || members.getRole().equals(FridgeRole.OWNER)) {
          members.setIsEnable(true);
          hasMember = true;
        }
      }
      if (!hasMember) {
        checkNewMember.add(FridgeUser.builder()
                .user(user)
                .role(FridgeRole.MEMBER)
                .fridge(fridgeUsers.get(0).getFridge())
                .build());
      }
    }
    return checkNewMember;
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
