package com.example.icebutler_server.fridge.service;

import com.example.icebutler_server.alarm.service.NotificationServiceImpl;
import com.example.icebutler_server.cart.repository.multiCart.MultiCartRepository;
import com.example.icebutler_server.food.dto.assembler.FoodAssembler;
import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.food.entity.FoodCategory;
import com.example.icebutler_server.food.entity.FoodDeleteStatus;
import com.example.icebutler_server.food.repository.FoodRepository;
import com.example.icebutler_server.fridge.dto.fridge.request.*;
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
import com.example.icebutler_server.global.sqs.AmazonSQSSender;
import com.example.icebutler_server.global.sqs.FoodData;
import com.example.icebutler_server.user.entity.User;
import com.example.icebutler_server.user.exception.UserNotFoundException;
import com.example.icebutler_server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
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
    private final MultiCartRepository multiCartRepository;

    private final MultiFridgeAssembler multiFridgeAssembler;
    private final MultiFridgeFoodAssembler multiFridgeFoodAssembler;
    private final FoodAssembler foodAssembler;

    private final AmazonSQSSender amazonSQSSender;
    private final NotificationServiceImpl notificationService;


    @Override
    public FridgeMainRes getFoods(Long fridgeIdx, Long userIdx, String category) {
        User user = this.userRepository.findByUserIdxAndIsEnable(userIdx, true).orElseThrow(UserNotFoundException::new);
        MultiFridge multiFridge = this.multiFridgeRepository.findByMultiFridgeIdxAndIsEnable(fridgeIdx, true).orElseThrow(FridgeNotFoundException::new);

        if(category == null){
            // 값이 없으면 전체 조회
            return FridgeMainRes.toMultiDto(this.multiFridgeFoodRepository.findByMultiFridgeForDisCardFood(multiFridge), this.multiFridgeFoodRepository.findByMultiFridgeAndIsEnableOrderByShelfLife(multiFridge, true));
        }else {
            // 값이 있으면 특정 값을 불러온 조회
            return FridgeMainRes.toMultiDto(this.multiFridgeFoodRepository.findByMultiFridgeForDisCardFood(multiFridge), this.multiFridgeFoodRepository.findByMultiFridgeAndFood_FoodCategoryAndIsEnableOrderByShelfLife(multiFridge, FoodCategory.getFoodCategoryByName(category), true));
        }
    }

    @Override
    @Transactional
    public Long registerFridge(FridgeRegisterReq registerFridgeReq, Long ownerIdx) {
        if (!StringUtils.hasText(registerFridgeReq.getFridgeName())) throw new FridgeNameEmptyException();
        MultiFridge multiFridge = multiFridgeAssembler.toEntity(registerFridgeReq);
        multiFridgeRepository.save(multiFridge);

        List<MultiFridgeUser> multiFridgeUsers = new ArrayList<>();
        List<User> users = registerFridgeReq.getMembers().stream().map(m -> userRepository.findByUserIdxAndIsEnable(m.getUserIdx(), true).orElseThrow(UserNotFoundException::new)).collect(Collectors.toList());
        User owner = userRepository.findByUserIdxAndIsEnable(ownerIdx, true).orElseThrow(UserNotFoundException::new);

        // multiFridge - multiFridgeUser  연관관계 추가
        for (User user : users) {
            multiFridgeUsers.add(MultiFridgeUser.builder().multiFridge(multiFridge).user(user).role(FridgeRole.MEMBER).build());
        }
        multiFridgeUsers.add(MultiFridgeUser.builder().multiFridge(multiFridge).user(owner).role(FridgeRole.OWNER).build());
        multiFridgeUserRepository.saveAll(multiFridgeUsers);

        // multiFridge - multiCart 연관관계 추가
        multiCartRepository.saveAll(multiFridgeAssembler.multiCartToEntity(multiFridgeUsers));

        return multiFridge.getMultiFridgeIdx();
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
    // 냉장고 삭제

    @Transactional
    @Override
    public Long removeFridge(Long fridgeIdx, Long userIdx) {
        User user = userRepository.findByUserIdxAndIsEnable(userIdx, true).orElseThrow(UserNotFoundException::new);
        MultiFridge fridge = multiFridgeRepository.findByMultiFridgeIdxAndIsEnable(fridgeIdx, true).orElseThrow(FridgeNotFoundException::new);
        multiFridgeUserRepository.findByMultiFridgeAndUserAndRoleAndIsEnable(fridge, user, FridgeRole.OWNER, true).orElseThrow(InvalidFridgeUserRoleException::new);
        List<MultiFridgeUser> users = multiFridgeUserRepository.findByMultiFridgeAndRoleAndIsEnable(fridge, FridgeRole.MEMBER, true);
        if(!users.isEmpty()) throw new FridgeRemoveException();
        this.multiFridgeRepository.delete(fridge);

        return fridge.getMultiFridgeIdx();
    }
    // 냉장고 개별 삭제

    @Transactional
    @Override
    public Long removeFridgeUser(Long fridgeIdx, Long userIdx) {
        User user = userRepository.findByUserIdxAndIsEnable(userIdx, true).orElseThrow(UserNotFoundException::new);
        MultiFridge fridge = multiFridgeRepository.findByMultiFridgeIdxAndIsEnable(fridgeIdx, true).orElseThrow(FridgeNotFoundException::new);
        MultiFridgeUser fridgeUser = multiFridgeUserRepository.findByMultiFridgeAndUserAndIsEnable(fridge, user, true).orElseThrow(FridgeUserNotFoundException::new);
        if(fridgeUser.getRole().equals(FridgeRole.OWNER))throw new PermissionDeniedException();
        multiFridgeUserRepository.delete(fridgeUser);
        return fridgeUser.getMultiFridgeUserIdx();
    }


    @Override
    public List<FridgeFoodsRes> searchFridgeFood(Long fridgeIdx, Long userIdx, String keyword){
        MultiFridge fridge = multiFridgeRepository.findByMultiFridgeIdxAndIsEnable(fridgeIdx, true).orElseThrow(FridgeNotFoundException::new);
        List<MultiFridgeFood> searchFoods = multiFridgeFoodRepository.findByFoodDetailNameContainingAndMultiFridgeAndIsEnable(keyword, fridge, true);
        return searchFoods.stream().map(FridgeFoodsRes::toMultiDto).collect(Collectors.toList());
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
                    .orElseGet(()->{
                        Food save = foodRepository.save(foodAssembler.toEntity(fridgeFoodReq));
                        amazonSQSSender.sendMessage(FoodData.toDto(save));
                        return save;
                    });
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
    @Transactional
    public void deleteFridgeFood(DeleteFridgeFoodsReq deleteFridgeFoodsReq, String type, Long multiFridgeIdx, Long userIdx) {
        FoodDeleteStatus deleteStatus = FoodDeleteStatus.getFoodDeleteStatusByName(type);
        User user = this.userRepository.findByUserIdxAndIsEnable(userIdx, true)
                .orElseThrow(UserNotFoundException::new);
        MultiFridge fridge = this.multiFridgeRepository.findByMultiFridgeIdxAndIsEnable(multiFridgeIdx, true)
                .orElseThrow(FridgeNotFoundException::new);
        this.multiFridgeUserRepository.findByMultiFridgeAndUserAndIsEnable(fridge, user, true)
                .orElseThrow(FridgeUserNotFoundException::new);

        List<MultiFridgeFood> deleteFridgeFoods = deleteFridgeFoodsReq.getDeleteFoods().stream()
                .map(foodIdx -> this.multiFridgeFoodRepository.findByMultiFridgeFoodIdxAndMultiFridgeAndIsEnable(foodIdx, fridge, true)
                        .orElseThrow(FridgeFoodNotFoundException::new))
                .collect(Collectors.toList());

        deleteFridgeFoods.forEach(food -> food.removeWithStatus(deleteStatus));
    }

    @Override
    public FridgeUserMainRes searchMembers (Long fridgeIdx, Long userIdx) {
        MultiFridge fridge=this.multiFridgeRepository.findByMultiFridgeIdxAndIsEnable(fridgeIdx,true).orElseThrow(FridgeNotFoundException::new);
        return FridgeUserMainRes.doMultiDto(multiFridgeUserRepository.findByMultiFridgeAndIsEnable(fridge,true));

    }
    // 통계

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

    @Override
    public RecipeFridgeFoodListsRes getFridgeUserFoodList(Long multiFridgeIdx, Long userIdx) {
        User user = userRepository.findByUserIdxAndIsEnable(userIdx, true).orElseThrow(UserNotFoundException::new);

        MultiFridge fridge = this.multiFridgeRepository.findByMultiFridgeIdxAndIsEnable(multiFridgeIdx, true).orElseThrow(FridgeNotFoundException::new);
        this.multiFridgeUserRepository.findByMultiFridgeAndUserAndIsEnable(fridge, user, true).orElseThrow(FridgeUserNotFoundException::new);
        return RecipeFridgeFoodListsRes.toDto(this.multiFridgeFoodRepository.findByUserForMultiFridgeRecipeFoodList(fridge));
    }

    @SneakyThrows
    @Transactional
    @Override
    public void notifyFridgeFood() {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.plusDays(3);
        List<MultiFridgeFood> list = multiFridgeFoodRepository.findByShelfLifeBetweenAndIsEnable(startDate, endDate, true);
        for (MultiFridgeFood multiFridgeFood : list) {
            MultiFridge multiFridge = multiFridgeFood.getMultiFridge();
            List<MultiFridgeUser> users = multiFridgeUserRepository.findByMultiFridgeAndIsEnable(multiFridge, true);
            for (MultiFridgeUser user : users) {
                notificationService.sendShelfLifeAlarm(user.getUser(), multiFridge.getFridgeName(), multiFridgeFood.getFood().getFoodName());
            }
        }
    }
}
