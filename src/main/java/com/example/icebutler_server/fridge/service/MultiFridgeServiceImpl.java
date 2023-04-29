package com.example.icebutler_server.fridge.service;

import com.example.icebutler_server.food.dto.assembler.FoodAssembler;
import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.food.entity.FoodCategory;
import com.example.icebutler_server.food.entity.FoodDeleteStatus;
import com.example.icebutler_server.food.repository.FoodRepository;
import com.example.icebutler_server.fridge.dto.fridge.request.DeleteFridgeFoodsReq;
import com.example.icebutler_server.fridge.dto.fridge.request.FridgeFoodReq;
import com.example.icebutler_server.fridge.dto.fridge.request.FridgeFoodsReq;
import com.example.icebutler_server.fridge.dto.fridge.request.FridgeModifyReq;
import com.example.icebutler_server.fridge.dto.fridge.response.*;
import com.example.icebutler_server.fridge.dto.multiFridge.assembler.MultiFridgeAssembler;
import com.example.icebutler_server.fridge.dto.multiFridge.assembler.MultiFridgeFoodAssembler;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridge;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridgeFood;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridgeUser;
import com.example.icebutler_server.fridge.exception.*;
import com.example.icebutler_server.fridge.repository.multiFridge.MultiFridgeFood.MultiFridgeFoodRepository;
import com.example.icebutler_server.fridge.repository.multiFridge.MultiFridgeRepository;
import com.example.icebutler_server.fridge.repository.multiFridge.MultiFridgeUserRepository;
import com.example.icebutler_server.global.entity.FridgeRole;
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
public class MultiFridgeServiceImpl implements FridgeService {
    private final UserRepository userRepository;
    private final FoodRepository foodRepository;
    private final MultiFridgeUserRepository multiFridgeUserRepository;
    private final MultiFridgeRepository multiFridgeRepository;
    private final MultiFridgeFoodRepository multiFridgeFoodRepository;

    private final MultiFridgeAssembler multiFridgeAssembler;
    private final MultiFridgeFoodAssembler multiFridgeFoodAssembler;
    private final FoodAssembler foodAssembler;


    @Override
    public FridgeMainRes getFoods(Long fridgeIdx, Long userIdx, String category) {
        User user = this.userRepository.findByUserIdxAndIsEnable(userIdx, true).orElseThrow(UserNotFoundException::new);
        MultiFridge multiFridge = this.multiFridgeRepository.findByMultiFridgeIdxAndIsEnable(fridgeIdx, true).orElseThrow(FridgeNotFoundException::new);

        if(category == null){
            // 값이 없으면 전체 조회
            return FridgeMainRes.toMultiDto(this.multiFridgeFoodRepository.findByMultiFridgeAndIsEnableOrderByShelfLife(multiFridge, true));
        }else {
            // 값이 있으면 특정 값을 불러온 조회
            return FridgeMainRes.toMultiDto(this.multiFridgeFoodRepository.findByMultiFridgeAndFood_FoodCategoryAndIsEnableOrderByShelfLife(multiFridge, FoodCategory.getFoodCategoryByName(category), true));
        }
    }

    // 멀티 냉장고 수정
    @Transactional
    @Override
    public void modifyFridge(Long fridgeIdx, FridgeModifyReq updateFridgeReq, Long userIdx){
        User user = this.userRepository.findByUserIdxAndIsEnable(userIdx, true).orElseThrow(UserNotFoundException::new);
        MultiFridge fridge = this.multiFridgeRepository.findByMultiFridgeIdxAndIsEnable(fridgeIdx, true).orElseThrow(FridgeNotFoundException::new);
        MultiFridgeUser owner = this.multiFridgeUserRepository.findByMultiFridgeAndUserAndRoleAndIsEnable(fridge, user, FridgeRole.OWNER, true).orElseThrow(InvalidFridgeUserRoleException::new);

        if(owner.getUser().getUserIdx()!=updateFridgeReq.getNewOwnerIdx()){
            MultiFridgeUser newOwner = this.multiFridgeUserRepository.findByMultiFridgeAndUser_UserIdxAndRoleAndIsEnableAndUser_IsEnable(fridge, updateFridgeReq.getNewOwnerIdx(), FridgeRole.MEMBER, true, true).orElseThrow(FridgeUserNotFoundException::new);
            this.multiFridgeAssembler.toUpdateFridgeOwner(owner, newOwner);
        }

        if(!StringUtils.hasText(updateFridgeReq.getFridgeName())) throw new FridgeNameEmptyException();
        this.multiFridgeAssembler.toUpdateBasicMultiFridgeInfo(fridge, updateFridgeReq);


        // todo: 프론트 샘들께 수정 로직 여쭤보고, 다시 코드 작성 필요
        if(updateFridgeReq.getMembers()!=null){
            List<MultiFridgeUser> members = this.multiFridgeUserRepository.findByMultiFridgeAndIsEnable(fridge, true);
            List<User> newMembers = updateFridgeReq.getMembers().stream()
                    .map(m -> this.userRepository.findByUserIdxAndIsEnable(m.getUserIdx(), true).orElseThrow(UserNotFoundException::new)).collect(Collectors.toList());
            List<MultiFridgeUser> checkNewMember = this.multiFridgeAssembler.toUpdateFridgeMembers(newMembers, members);

            if(!checkNewMember.isEmpty()){
                this.multiFridgeUserRepository.saveAll(checkNewMember);
            }
        }
    }

    @Override
    public Long removeFridge(Long fridgeIdx, Long userIdx) {
        return null;
    }


    @Override
    public SearchFridgeFoodRes searchFridgeFood(Long fridgeIdx, Long ownerIdx, String foodName){
        return null;
    }

    @Override
    public FridgeFoodRes getFridgeFood(Long fridgeIdx, Long fridgeFoodIdx, Long userIdx) {
        userRepository.findByUserIdxAndIsEnable(userIdx, true).orElseThrow(UserNotFoundException::new);
        multiFridgeRepository.findByMultiFridgeIdxAndIsEnable(fridgeIdx, true).orElseThrow(FridgeNotFoundException::new);
        multiFridgeUserRepository.findByUser_UserIdxAndMultiFridge_MultiFridgeIdxAndIsEnable(userIdx, fridgeIdx, true).orElseThrow(FridgeUserNotFoundException::new);
        MultiFridgeFood fridgeFood = multiFridgeFoodRepository.findById(fridgeFoodIdx).orElseThrow(FridgeFoodNotFoundException::new);

        return FridgeFoodRes.toDto(fridgeFood);
    }

    // 냉장고 내 식품 추가
    @Transactional
    @Override
    public void addFridgeFood(FridgeFoodsReq fridgeFoodsReq, Long fridgeIdx, Long userIdx) {
        User user = userRepository.findByUserIdxAndIsEnable(userIdx, true).orElseThrow(UserNotFoundException::new);
        MultiFridge fridge = this.multiFridgeRepository.findByMultiFridgeIdxAndIsEnable(fridgeIdx, true).orElseThrow(FridgeNotFoundException::new);
        this.multiFridgeUserRepository.findByMultiFridgeAndUserAndIsEnable(fridge, user, true).orElseThrow(FridgeUserNotFoundException::new);

        List<MultiFridgeFood> fridgeFoods = new ArrayList<>();
        for (FridgeFoodReq fridgeFoodReq : fridgeFoodsReq.getFridgeFoods()){
            User owner = null;
            if(fridgeFoodReq.getOwnerIdx() != null) {
                owner = userRepository.findByUserIdxAndIsEnable(fridgeFoodReq.getOwnerIdx(), true).orElseThrow(UserNotFoundException::new);
                multiFridgeUserRepository.findByMultiFridgeAndUserAndIsEnable(fridge, user, true).orElseThrow(FridgeUserNotFoundException::new);
            }
            Food food = foodRepository.findByFoodName(fridgeFoodReq.getFoodName())
                    .orElseGet(() -> foodRepository.save(foodAssembler.toEntity(fridgeFoodReq)));
            fridgeFoods.add(multiFridgeFoodAssembler.toEntity(owner, fridge, food, fridgeFoodReq));
        }
        multiFridgeFoodRepository.saveAll(fridgeFoods);
    }

    // 냉장고 식품 수정
    @Transactional
    @Override
    public void modifyFridgeFood(Long fridgeIdx, Long fridgeFoodIdx, FridgeFoodReq fridgeFoodReq, Long userIdx) {
        User user = this.userRepository.findByUserIdxAndIsEnable(userIdx, true).orElseThrow(UserNotFoundException::new);
        MultiFridge fridge = this.multiFridgeRepository.findByMultiFridgeIdxAndIsEnable(fridgeIdx, true).orElseThrow(FridgeNotFoundException::new);
        this.multiFridgeUserRepository.findByMultiFridgeAndUserAndIsEnable(fridge, user, true).orElseThrow(FridgeUserNotFoundException::new);
        MultiFridgeFood modifyMultiFridgeFood = this.multiFridgeFoodRepository.findByMultiFridgeFoodIdxAndMultiFridgeAndIsEnable(fridgeFoodIdx, fridge, true).orElseThrow(FridgeFoodNotFoundException::new);

        if(!modifyMultiFridgeFood.getFood().getFoodName().equals(fridgeFoodReq.getFoodName())) {
            Food food = this.foodRepository.findByFoodName(fridgeFoodReq.getFoodName())
                    .orElseGet(()->foodRepository.save(this.foodAssembler.toEntity(fridgeFoodReq)));
            this.multiFridgeFoodAssembler.toUpdateMultiFridgeFoodInfo(modifyMultiFridgeFood, food);
        }

        this.multiFridgeFoodAssembler.toUpdateBasicMultiFridgeFoodInfo(modifyMultiFridgeFood, fridgeFoodReq);

        if(!modifyMultiFridgeFood.getOwner().getUserIdx().equals(fridgeFoodReq.getOwnerIdx()) && fridgeFoodReq.getOwnerIdx() != null){
            User newOwner = this.userRepository.findByUserIdxAndIsEnable(fridgeFoodReq.getOwnerIdx(), true).orElseThrow(UserNotFoundException::new);
            this.multiFridgeUserRepository.findByMultiFridgeAndUserAndIsEnable(fridge, newOwner, true).orElseThrow(FridgeUserNotFoundException::new);
            this.multiFridgeFoodAssembler.toUpdateMultiFridgeFoodOwner(modifyMultiFridgeFood, newOwner);
        }
    }

    @Override
    public void deleteFridgeFood(DeleteFridgeFoodsReq deleteFridgeFoodsReq, String deleteType, Long fridgeIdx, Long userIdx) {

    }

    @Override
    public FridgeUserMainRes searchMembers (Long fridgeIdx, Long userIdx) {
        MultiFridge fridge=this.multiFridgeRepository.findByMultiFridgeIdxAndIsEnable(fridgeIdx,true).orElseThrow(FridgeNotFoundException::new);
        return FridgeUserMainRes.doMultiDto(multiFridgeUserRepository.findByMultiFridgeAndIsEnable(fridge,true));

    }

    public FridgeFoodsStatistics getFridgeFoodStatistics(Long multiFridgeIdx, String deleteCategory, Long userIdx, Integer year, Integer month) {
        User user = this.userRepository.findByUserIdxAndIsEnable(userIdx,true).orElseThrow(UserNotFoundException::new);
        MultiFridge fridge = this.multiFridgeRepository.findByMultiFridgeIdxAndIsEnable(multiFridgeIdx,true).orElseThrow(FridgeNotFoundException::new);
        this.multiFridgeUserRepository.findByMultiFridgeAndUserAndIsEnable(fridge, user, true).orElseThrow(FridgeUserNotFoundException::new);

        Map<FoodCategory, Long> deleteStatusList = new HashMap<>();

        for(FoodCategory category: FoodCategory.values()){
            Long foodSize = this.multiFridgeFoodRepository.findByDeleteCategoryForStatistics(FoodDeleteStatus.getFoodDeleteStatusByName(deleteCategory), fridge, category, year, month);
            deleteStatusList.put(category, foodSize);
        }

        return this.multiFridgeFoodAssembler.toFoodStatisticsByDeleteStatus(deleteStatusList);
    }
}
