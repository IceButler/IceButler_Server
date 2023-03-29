package com.example.icebutler_server.fridge.service;

import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.fridge.dto.AddFridgeReq;
import com.example.icebutler_server.fridge.dto.FridgeFoodsRes;
import com.example.icebutler_server.fridge.entity.Fridge;
import com.example.icebutler_server.fridge.entity.FridgeUser;
import com.example.icebutler_server.fridge.repository.FridgeRepository;
import com.example.icebutler_server.global.exception.BaseException;
import com.example.icebutler_server.user.entity.User;
import com.example.icebutler_server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static com.example.icebutler_server.global.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class FridgeService {

  private final FridgeRepository fridgeRepository;
  private final UserRepository userRepository;

  @Transactional
  public String addFridge(Long ownerId, AddFridgeReq addFridgeReq) throws BaseException {

    User user = userRepository.findById(ownerId).orElseThrow(() -> new BaseException(NULL_FRIDGE_IDX));

    if (addFridgeReq.getFridgeName().equals("")) {
      throw new BaseException(NULL_FRIDGE_NAME);
    } else {
      Fridge fridge = Fridge.builder()
              .owner(user)
              .fridgeName(addFridgeReq.getFridgeName())
              .fridgeComment(addFridgeReq.getDescription())
              .fridgeUsers(addFridgeReq.getUsers())
              .build();

      fridgeRepository.save(fridge);
    }
    return "addSuccess";
  }

  @Transactional
  public String removeFridge(Long fridgeId) throws BaseException {
    Fridge fridge = fridgeRepository.findById(fridgeId).orElseThrow(() ->
            new BaseException(NULL_FRIDGE_IDX));
    fridgeRepository.delete(fridge);
    return "removeSuccess";
  }

  @Transactional
  public FridgeFoodsRes getFoods(Long ownerId, Long fridgeId) throws BaseException {

    List<List<Food>> usersFoods = new ArrayList<>();

    //냉장고 오너가 추가한 음식들
    User user = userRepository.findById(ownerId).orElseThrow(() -> new BaseException(NULL_OWNER_IDX));
    List<Food> ownerFoods = user.getFoods();

    //냉장고 멤버가 추가한 음식들
    Fridge fridge = fridgeRepository.findById(fridgeId).orElseThrow(() -> new BaseException(NULL_FRIDGE_IDX));

    List<FridgeUser> fridgeUsers = fridge.getFridgeUsers();
    for (FridgeUser users : fridgeUsers) {
      usersFoods.add(users.getOwner().getFoods());
    }

    return FridgeFoodsRes.builder()
            .ownerFoods(ownerFoods)
            .fridgeUserFoods(usersFoods)
            .build();
  }

  @Transactional
  public List<Food> findFoodByName(Long fridgeId, Long ownerId, String foodName) throws BaseException {

    List<Food> searchFood = new ArrayList<>();

    //냉장고 오너가 등록한 음식 중 검색
    User user = userRepository.findById(ownerId).orElseThrow(() -> new BaseException(NULL_OWNER_IDX));
    List<Food> ownerFoods = user.getFoods();
    for (Food food : ownerFoods) {
      if (food.getFoodName().equals(foodName)) searchFood.add(food);
    }

    //냉장고 멤버가 등록한 음식 중 검색
    Fridge fridge = fridgeRepository.findById(fridgeId).orElseThrow(() -> new BaseException(NULL_FRIDGE_IDX));
    List<FridgeUser> fridgeUsers = fridge.getFridgeUsers();
    for (FridgeUser fridgeUser : fridgeUsers) {
      for (Food food : fridgeUser.getOwner().getFoods()) {
        if (food.getFoodName().equals(foodName)) searchFood.add(food);
      }
    }
    // 검색결과 x
    if (searchFood.size() == 0) throw new BaseException(NULL_SEARCH_FOOD);
    return searchFood;
  }
}
