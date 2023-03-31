package com.example.icebutler_server.fridge.dto;

import com.example.icebutler_server.fridge.Repository.FridgeRepository;
import com.example.icebutler_server.fridge.Repository.FridgeUserRepository;
import com.example.icebutler_server.fridge.dto.request.CreateFridgeReq;
import com.example.icebutler_server.fridge.dto.request.UpdateFridgeReq;
import com.example.icebutler_server.fridge.dto.response.FridgeFoodsRes;
import com.example.icebutler_server.fridge.dto.response.FridgeRes;
import com.example.icebutler_server.fridge.dto.response.FridgeUserRes;
import com.example.icebutler_server.fridge.entity.Food;
import com.example.icebutler_server.fridge.entity.Fridge;
import com.example.icebutler_server.fridge.entity.FridgeUser;
import com.example.icebutler_server.global.exception.BaseException;
import com.example.icebutler_server.user.entity.User;
import com.example.icebutler_server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.icebutler_server.global.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class FridgeAssembler {

  private final UserRepository userRepository;
  private final FridgeRepository fridgeRepository;
  private final FridgeUserRepository fridgeUserRepository;

  public Fridge toEntity(CreateFridgeReq createFridgeReq, User user) {
    return Fridge.builder()
            .owner(user)
            .fridgeName(createFridgeReq.getFridgeName())
            .fridgeComment(createFridgeReq.getFridgeComment())
            .fridgeUsers(createFridgeReq.getUsers())
            .build();
  }

  public FridgeRes toDto(Fridge fridge) {
    FridgeRes fridgeRes = new FridgeRes();
    fridgeRes.setOwnerNickname(fridge.getOwner().getNickname());
    fridgeRes.setFridgeName(fridge.getFridgeName());
    fridgeRes.setComment(fridge.getFridgeComment());
    fridgeRes.setUsers(fridge.getFridgeUsers().stream()
            .map((fu) -> FridgeUserRes.toDto(fu.getUser()))
            .collect(Collectors.toList()));
    return fridgeRes;
  }

  public boolean isEmptyFridgeName(CreateFridgeReq createFridgeReq) {
    return createFridgeReq.getFridgeName().isEmpty();
  }

  public Fridge toUpdateEntity(UpdateFridgeReq updateFridgeReq) {
    return Fridge.builder()
            .fridgeName(updateFridgeReq.getFridgeName())
            .fridgeComment(updateFridgeReq.getFridgeComment())
            .build();
  }

  public User updateFridgeOwner(UpdateFridgeReq updateFridgeReq, Long userId) {
    User newOwner = userRepository.findByNickname(updateFridgeReq.getNewOwnerName());
    User originOwner = userRepository.findById(userId).orElseThrow();
    if (newOwner != null) return newOwner;
    return originOwner;
  }

  public List<FridgeUser> updateMembers(UpdateFridgeReq updateFridgeReq) {
    List<FridgeUser> fridgeUsers = new ArrayList<>();
    for (String name : updateFridgeReq.getUsersName()) {
      fridgeUsers.add(fridgeUserRepository.findByUser(userRepository.findByNickname(name)));
    }
    return fridgeUsers;
  }

  public FridgeFoodsRes getFridgeFoods(Long ownerId, Long fridgeId) throws BaseException {
    //냉장고 오너가 추가한 음식들
    User user = userRepository.findById(ownerId).orElseThrow(() -> new BaseException(NULL_OWNER_IDX));
    List<Food> ownerFoods = user.getFoods();

    //냉장고 멤버가 추가한 음식들
    Fridge fridge = fridgeRepository.findById(fridgeId).orElseThrow(() -> new BaseException(NULL_FRIDGE_IDX));
    List<List<Food>> usersFoods = new ArrayList<>();

    List<FridgeUser> fridgeUsers = fridge.getFridgeUsers();
    for (FridgeUser users : fridgeUsers) {
      usersFoods.add(users.getUser().getFoods());
    }
    return FridgeFoodsRes.builder()
            .ownerFoods(ownerFoods)
            .fridgeUserFoods(usersFoods)
            .build();
  }

  public List<Food> findFoodByFoodName(Long fridgeId, Long ownerId, String foodName) throws BaseException {
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
      for (Food food : fridgeUser.getUser().getFoods()) {
        if (food.getFoodName().equals(foodName)) searchFood.add(food);
      }
    }
    // 검색결과 x
    if (searchFood.size() == 0) throw new BaseException(NULL_SEARCH_FOOD);
    return searchFood;
  }
}
