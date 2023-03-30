package com.example.icebutler_server.fridge.service;

import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.fridge.dto.request.CreateFridgeReq;
import com.example.icebutler_server.fridge.dto.request.UpdateFridgeReq;
import com.example.icebutler_server.fridge.dto.response.FridgeFoodsRes;
import com.example.icebutler_server.fridge.dto.response.FridgeRes;
import com.example.icebutler_server.fridge.dto.updateFridgeRes;
import com.example.icebutler_server.fridge.entity.Fridge;
import com.example.icebutler_server.fridge.entity.FridgeUser;
import com.example.icebutler_server.fridge.repository.FridgeRepository;
import com.example.icebutler_server.fridge.repository.FridgeUserRepository;
import com.example.icebutler_server.global.dto.response.ResponseCustom;
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
  private final FridgeUserRepository fridgeUserRepository;
  private final UserRepository userRepository;

  @Transactional
  public ResponseCustom<FridgeRes> createFridge(CreateFridgeReq createFridgeReq) throws BaseException {

    User user = userRepository.findById(createFridgeReq.getOwner()).orElseThrow(() -> new BaseException(NULL_FRIDGE_IDX));

    /* 아래 addFridgeReq.getFridgeName().equals("") 보다는 isEmpty 사용하기 */
    if (createFridgeReq.getFridgeName().isEmpty()) throw new BaseException(NULL_FRIDGE_NAME);
    /* 여기에 else문 있었는데 불필요해서 제거 */
    Fridge fridge = Fridge.builder()
              .owner(user)
              .fridgeName(createFridgeReq.getFridgeName())
              .fridgeComment(createFridgeReq.getDescription())
              .fridgeUsers(createFridgeReq.getUsers())
              .build();
    fridgeRepository.save(fridge);
    return ResponseCustom.CREATED(FridgeRes.toDto(fridge)); /* addFridge의 반환타입을 지정해주지 않아서 그전에 에어 났던듯..? */
  }

  @Transactional
  public ResponseCustom<?> updateFridge(UpdateFridgeReq updateFridgeReq) throws BaseException {
    Fridge fridge = fridgeRepository.findById(updateFridgeReq.getFridgeId()).orElseThrow();
    User newOwner = userRepository.findByNickname(updateFridgeReq.getNewOwnerName());
    List<FridgeUser> newFridgeUsers = new ArrayList<>();

    // 냉장고 주인 업데이트
    if(newOwner != null){
      fridge.changOwner(newOwner);
    }
    // 냉장고 멤버 업데이트
    for (String nickName : updateFridgeReq.getUsersName()) {
      newFridgeUsers.add(fridgeUserRepository.findByOwner(userRepository.findByNickname(nickName)));
    }
    if(!newFridgeUsers.isEmpty()){
      fridge.changeFridgeUsers(newFridgeUsers);
    }
    // 냉장고 이름 업데이트
    if (!updateFridgeReq.getFridgeName().isEmpty()) {
      fridge.changeFridgeName(updateFridgeReq.getFridgeName());
    }
    // 냉장고 설명 업데이트
    if (!updateFridgeReq.getFridgeComment().isEmpty()) {
      fridge.changeFridgeComment(updateFridgeReq.getFridgeComment());
    }

    return ResponseCustom.OK(SUCCESS);
  }

  @Transactional
  public ResponseCustom<Long> deleteFridge(Long fridgeId) throws BaseException {
    Fridge fridge = fridgeRepository.findById(fridgeId).orElseThrow(() -> new BaseException(NULL_FRIDGE_IDX));
    fridgeRepository.delete(fridge);
    return ResponseCustom.OK(fridge.getFridgeIdx());
  }

  @Transactional
  public FridgeFoodsRes getFoods(Long ownerId, Long fridgeId) throws BaseException {
    //냉장고 오너가 추가한 음식들
    User user = userRepository.findById(ownerId).orElseThrow(() -> new BaseException(NULL_OWNER_IDX));
    List<Food> ownerFoods = user.getFoods();

    //냉장고 멤버가 추가한 음식들
    Fridge fridge = fridgeRepository.findById(fridgeId).orElseThrow(() -> new BaseException(NULL_FRIDGE_IDX));
    List<List<Food>> usersFoods = new ArrayList<>();

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
