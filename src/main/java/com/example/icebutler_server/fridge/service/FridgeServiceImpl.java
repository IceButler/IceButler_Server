package com.example.icebutler_server.fridge.service;

import com.example.icebutler_server.alarm.service.NotificationServiceImpl;
import com.example.icebutler_server.cart.dto.cart.assembler.CartAssembler;
import com.example.icebutler_server.cart.repository.cart.CartRepository;
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

import java.io.IOException;
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
    private final CartRepository cartRepository;

    private final FridgeAssembler fridgeAssembler;
    private final FridgeFoodAssembler fridgeFoodAssembler;
    private final FoodAssembler foodAssembler;
    private final CartAssembler cartAssembler;

    private final AmazonSQSSender amazonSQSSender;
    private final NotificationServiceImpl alarmService;

    @Override
    public FridgeMainRes getFoods(Long fridgeIdx, Long userIdx, String category) {
        User user = this.userRepository.findByIdAndIsEnable(userIdx, true).orElseThrow(UserNotFoundException::new);
        Fridge fridge = this.fridgeRepository.findByIdAndIsEnable(fridgeIdx, true).orElseThrow(FridgeNotFoundException::new);

        if (category == null) {
            // 값이 없으면 전체 조회
            return FridgeMainRes.toFridgeDto(this.fridgeFoodRepository.findByFridgeForDisCardFood(fridge), this.fridgeFoodRepository.findByFridgeAndIsEnableOrderByShelfLife(fridge, true));
        } else {
            // 값이 있으면 특정 값을 불러온 조회
            return FridgeMainRes.toFridgeDto(this.fridgeFoodRepository.findByFridgeForDisCardFood(fridge), this.fridgeFoodRepository.findByFridgeAndFood_FoodCategoryAndIsEnableOrderByShelfLife(fridge, FoodCategory.getFoodCategoryByName(category), true));
        }
    }

    @Override
    @Transactional
    public Long registerFridge(FridgeRegisterReq registerFridgeReq, Long ownerIdx) {
        if (!StringUtils.hasText(registerFridgeReq.getFridgeName())) throw new FridgeNameEmptyException();
        Fridge fridge = fridgeAssembler.toEntity(registerFridgeReq);
        fridgeRepository.save(fridge);

        List<FridgeUser> fridgeUsers = new ArrayList<>();
        List<User> users = registerFridgeReq.getMembers().stream().map(m -> userRepository.findByIdAndIsEnable(m.getUserIdx(), true).orElseThrow(UserNotFoundException::new)).collect(Collectors.toList());
        User owner = userRepository.findByIdAndIsEnable(ownerIdx, true).orElseThrow(UserNotFoundException::new);

        // fridge - fridgeUser  연관관계 추가
        for (User user : users) {
            fridgeUsers.add(FridgeUser.builder().fridge(fridge).user(user).role(FridgeRole.MEMBER).build());
        }
        fridgeUsers.add(FridgeUser.builder().fridge(fridge).user(owner).role(FridgeRole.OWNER).build());
        fridgeUserRepository.saveAll(fridgeUsers);

        // fridge - cart 연관관계 추가
        cartRepository.save(cartAssembler.toEntity(fridge));

        users.forEach(f -> {
            try {
                alarmService.sendJoinFridgeAlarm(f, fridge.getFridgeName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return fridge.getId();
    }

    @Override
    @Transactional
    public void modifyFridge(Long fridgeIdx, FridgeModifyReq updateFridgeReq, Long userIdx) {
        User user = this.userRepository.findByIdAndIsEnable(userIdx, true).orElseThrow(UserNotFoundException::new);
        Fridge fridge = this.fridgeRepository.findByIdAndIsEnable(fridgeIdx, true).orElseThrow(FridgeNotFoundException::new);
        FridgeUser owner = this.fridgeUserRepository.findByFridgeAndUserAndRoleAndIsEnable(fridge, user, FridgeRole.OWNER, true).orElseThrow(InvalidFridgeUserRoleException::new);

        // 오너 업데이트
        if (!owner.getUser().getId().equals(updateFridgeReq.getNewOwnerIdx())) {
            FridgeUser newOwner = this.fridgeUserRepository.findByFridgeAndUser_IdAndRoleAndIsEnableAndUser_IsEnable(fridge, updateFridgeReq.getNewOwnerIdx(), FridgeRole.MEMBER, true, true).orElseThrow(FridgeUserNotFoundException::new);
            this.fridgeAssembler.toUpdateFridgeOwner(owner, newOwner);
        }

        // 냉장고 정보 (이름, 설명) 업데이트
        if (!StringUtils.hasText(updateFridgeReq.getFridgeName())) throw new FridgeNameEmptyException();
        this.fridgeAssembler.toUpdateBasicMultiFridgeInfo(fridge, updateFridgeReq);

        // 멤버 업데이트
        if (updateFridgeReq.getMembers() != null) {
            List<FridgeUser> members = this.fridgeUserRepository.findByFridgeAndIsEnable(fridge, true);
            List<User> newMembers = updateFridgeReq.getMembers().stream()
                    .map(m -> this.userRepository.findByIdAndIsEnable(m.getUserIdx(), true).orElseThrow(UserNotFoundException::new)).collect(Collectors.toList());
            UpdateMembersRes updateMembers = this.fridgeAssembler.toUpdateFridgeMembers(newMembers, members);

            if (!updateMembers.getCheckNewMember().isEmpty()) {
                this.fridgeUserRepository.saveAll(updateMembers.getCheckNewMember());
            }

            updateMembers.getWithDrawMember().forEach(f -> {
                try {
                    alarmService.sendWithdrawalAlarm(f.getUser(), f.getFridge().getFridgeName());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            updateMembers.getCheckNewMember().forEach(f -> {
                try {
                    alarmService.sendJoinFridgeAlarm(f.getUser(), f.getFridge().getFridgeName());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        }
    }

    // 냉장고 자체 삭제
    @Transactional
    public Long removeFridge(Long fridgeIdx, Long userId) {
        User user = userRepository.findByIdAndIsEnable(userId, true).orElseThrow(UserNotFoundException::new);
        Fridge fridge = fridgeRepository.findByIdAndIsEnable(fridgeIdx, true).orElseThrow(FridgeNotFoundException::new);
        FridgeUser owner = (FridgeUser) fridgeUserRepository.findByUserAndFridgeAndIsEnable(user, fridge, true).orElseThrow(FridgeUserNotFoundException::new);
        List<FridgeUser> fridgeUsers = fridgeUserRepository.findByFridgeAndIsEnable(fridge, true);
        List<FridgeFood> fridgeFoods = fridgeFoodRepository.findByFridgeAndIsEnableOrderByShelfLife(fridge, true);

        fridgeAssembler.removeFridge(owner, fridge, fridgeUsers, fridgeFoods);
        fridgeFoodRepository.removeFridgeFoodByFridge(false, fridge);

        return fridge.getId();
    }

    // 냉장고 개별
    @Override
    @Transactional
    public Long removeFridgeUser(Long fridgeIdx, Long userIdx) {
        User user = userRepository.findByIdAndIsEnable(userIdx, true).orElseThrow(UserNotFoundException::new);
        Fridge fridge = fridgeRepository.findByIdAndIsEnable(fridgeIdx, true).orElseThrow(FridgeNotFoundException::new);
        FridgeUser fridgeUser = (FridgeUser) fridgeUserRepository.findByUserAndFridgeAndIsEnable(user, fridge, true).orElseThrow(FridgeUserNotFoundException::new);

        if (fridgeUser.getRole() == FridgeRole.OWNER) throw new PermissionDeniedException();
        fridgeUser.remove();

        return fridge.getId();
    }

    @Override
    public List<FridgeFoodsRes> searchFridgeFood(Long fridgeIdx, Long userIdx, String keyword) {
        Fridge fridge = fridgeRepository.findByIdAndIsEnable(fridgeIdx, true).orElseThrow(FridgeNotFoundException::new);
        List<FridgeFood> searchFoods = fridgeFoodRepository.findByFoodDetailNameContainingAndFridgeAndIsEnable(keyword, fridge, true);
        return searchFoods.stream().map(FridgeFoodsRes::toDto).collect(Collectors.toList());
    }

    @Override
    public FridgeFoodRes getFridgeFood(Long fridgeIdx, Long fridgeFoodIdx, Long userIdx) {
        User user = userRepository.findByIdAndIsEnable(userIdx, true).orElseThrow(UserNotFoundException::new);
        Fridge fridge = fridgeRepository.findByIdAndIsEnable(fridgeIdx, true).orElseThrow(FridgeNotFoundException::new);
        fridgeUserRepository.findByUserAndFridgeAndIsEnable(user, fridge, true).orElseThrow(FridgeUserNotFoundException::new);
        FridgeFood fridgeFood = fridgeFoodRepository.findByIdAndFridgeAndIsEnable(fridgeFoodIdx, fridge, true).orElseThrow(FridgeFoodNotFoundException::new);

        return FridgeFoodRes.toDto(fridgeFood);
    }

    @Override
    @Transactional
    public void addFridgeFood(FridgeFoodsReq fridgeFoodsReq, Long fridgeIdx, Long userIdx) {
        User user = userRepository.findByIdAndIsEnable(userIdx, true).orElseThrow(UserNotFoundException::new);
        Fridge fridge = fridgeRepository.findByIdAndIsEnable(fridgeIdx, true).orElseThrow(FridgeNotFoundException::new);
        fridgeUserRepository.findByUserAndFridgeAndIsEnable(user, fridge, true).orElseThrow(FridgeUserNotFoundException::new);

        List<FridgeFood> fridgeFoods = new ArrayList<>();
        for (FridgeFoodReq fridgeFoodReq : fridgeFoodsReq.getFridgeFoods()) {
            User owner = null;
            if (fridgeFoodReq.getOwnerIdx() != null) {
                owner = userRepository.findByIdAndIsEnable(fridgeFoodReq.getOwnerIdx(), true).orElseThrow(UserNotFoundException::new);
                fridgeUserRepository.findByUserAndFridgeAndIsEnable(owner, fridge, true).orElseThrow(FridgeUserNotFoundException::new);
            }
            Food food = foodRepository.findByFoodName(fridgeFoodReq.getFoodName())
                    .orElseGet(() -> {
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
        User user = this.userRepository.findByIdAndIsEnable(userIdx, true)
                .orElseThrow(UserNotFoundException::new);
        Fridge fridge = this.fridgeRepository.findByIdAndIsEnable(fridgeIdx, true)
                .orElseThrow(FridgeNotFoundException::new);
        this.fridgeUserRepository.findByFridgeAndUserAndIsEnable(fridge, user, true)
                .orElseThrow(FridgeUserNotFoundException::new);
        FridgeFood modifyFridgeFood = this.fridgeFoodRepository.findByIdAndFridgeAndIsEnable(fridgeFoodIdx, fridge, true)
                .orElseThrow(FridgeFoodNotFoundException::new);

        if (!modifyFridgeFood.getFood().getFoodName().equals(fridgeFoodReq.getFoodName())) {
            Food food = this.foodRepository.findByFoodName(fridgeFoodReq.getFoodName())
                    .orElseGet(() -> {
                        Food save = foodRepository.save(foodAssembler.toEntity(fridgeFoodReq));
                        amazonSQSSender.sendMessage(FoodData.toDto(save));
                        return save;
                    });
            this.fridgeFoodAssembler.toUpdateFridgeFoodInfo(modifyFridgeFood, food);
        }

        this.fridgeFoodAssembler.toUpdateBasicFridgeFoodInfo(modifyFridgeFood, fridgeFoodReq);

        if (fridgeFoodReq.getOwnerIdx() == null)
            this.fridgeFoodAssembler.toUpdateFridgeFoodOwner(modifyFridgeFood, null);
        else {
            User newOwner = this.userRepository.findByIdAndIsEnable(fridgeFoodReq.getOwnerIdx(), true)
                    .orElseThrow(UserNotFoundException::new);
            this.fridgeUserRepository.findByFridgeAndUserAndIsEnable(fridge, newOwner, true)
                    .orElseThrow(FridgeUserNotFoundException::new);
            if (!newOwner.equals(modifyFridgeFood.getOwner()))
                this.fridgeFoodAssembler.toUpdateFridgeFoodOwner(modifyFridgeFood, newOwner);
        }
    }

    @Override
    @Transactional
    public void deleteFridgeFood(DeleteFridgeFoodsReq deleteFridgeFoodsReq, String type, Long fridgeIdx, Long userIdx) {
        FoodDeleteStatus deleteStatus = FoodDeleteStatus.getFoodDeleteStatusByName(type);
        User user = this.userRepository.findByIdAndIsEnable(userIdx, true)
                .orElseThrow(UserNotFoundException::new);
        Fridge fridge = this.fridgeRepository.findByIdAndIsEnable(fridgeIdx, true)
                .orElseThrow(FridgeNotFoundException::new);
        this.fridgeUserRepository.findByFridgeAndUserAndIsEnable(fridge, user, true)
                .orElseThrow(FridgeUserNotFoundException::new);

        List<FridgeFood> deleteFridgeFoods = deleteFridgeFoodsReq.getDeleteFoods().stream()
                .map(foodIdx -> this.fridgeFoodRepository.findByIdAndFridgeAndIsEnable(foodIdx, fridge, true)
                        .orElseThrow(FridgeFoodNotFoundException::new))
                .collect(Collectors.toList());

        deleteFridgeFoods.forEach(food -> food.removeWithStatus(deleteStatus));
    }

    @Override
    //냉장고 내 유저 조회
    public FridgeUserMainRes searchMembers(Long fridgeIdx, Long userIdx) {
        Fridge fridge = fridgeRepository.findByIdAndIsEnable(fridgeIdx, true)
                .orElseThrow(FridgeNotFoundException::new);
        return FridgeUserMainRes.doDto(fridgeUserRepository.findByFridgeAndIsEnable(fridge, true));
    }

    @Override
    public FridgeFoodsStatistics getFridgeFoodStatistics(Long fridgeIdx, String deleteCategory, Long userIdx, Integer year, Integer month) {
        User user = this.userRepository.findByIdAndIsEnable(userIdx, true).orElseThrow(UserNotFoundException::new);
        Fridge fridge = this.fridgeRepository.findByIdAndIsEnable(fridgeIdx, true).orElseThrow(FridgeNotFoundException::new);
        this.fridgeUserRepository.findByFridgeAndUserAndIsEnable(fridge, user, true).orElseThrow(FridgeUserNotFoundException::new);

        Map<FoodCategory, Long> deleteStatusList = new HashMap<>();

        for (FoodCategory category : FoodCategory.values()) {
            Long foodSize = this.fridgeFoodRepository.findByDeleteCategoryForStatistics(FoodDeleteStatus.getFoodDeleteStatusByName(deleteCategory), fridge, category, year, month);
            deleteStatusList.put(category, foodSize);
        }

        return this.fridgeFoodAssembler.toFoodStatisticsByDeleteStatus(deleteStatusList);
    }

    public SelectFridgesMainRes selectFridges(Long userIdx) {
        User user = userRepository.findByIdAndIsEnable(userIdx, true).orElseThrow(UserNotFoundException::new);
        return SelectFridgesMainRes.toDto(fridgeUserRepository.findByUserAndIsEnable(user, true), multiFridgeUserRepository.findByUserAndIsEnable(user, true));
    }

    public GetFridgesMainRes myFridge(Long userIdx) {
        User user = userRepository.findByIdAndIsEnable(userIdx, true).orElseThrow(UserNotFoundException::new);

        // 가정용 냉장고 조회
        List<FridgeUser> fridgeUsers = fridgeUserRepository.findByUserAndIsEnable(user, true);
        List<Fridge> fridges = fridgeUsers.stream().map(m -> fridgeRepository.findByIdAndIsEnable(m.getFridge().getId(), true).orElseThrow(FridgeNotFoundException::new)).collect(Collectors.toList());
        List<List<FridgeUser>> fridgeUserListList = fridges.stream().map(m -> fridgeUserRepository.findByFridgeAndIsEnableOrderByRoleDesc(m, true)).collect(Collectors.toList());

        // 공용 냉장고 조회
        List<MultiFridgeUser> multiFridgeUsers = multiFridgeUserRepository.findByUserAndIsEnable(user, true);
        List<MultiFridge> multiFridges = multiFridgeUsers.stream().map(m -> multiFridgeRepository.findByIdAndIsEnable(m.getMultiFridge().getId(), true).orElseThrow(FridgeNotFoundException::new)).collect(Collectors.toList());
        List<List<MultiFridgeUser>> multiFridgeUserListList = multiFridges.stream().map(m -> multiFridgeUserRepository.findByMultiFridgeAndIsEnableOrderByRoleDesc(m, true)).collect(Collectors.toList());

        return GetFridgesMainRes.toDto(fridgeUserListList, multiFridgeUserListList, userIdx);

    }

    //  사용자가 속한 가정용/공용 냉장고 food list
    public RecipeFridgeFoodListsRes getFridgeUserFoodList(Long fridgeIdx, Long userIdx) {
        User user = userRepository.findByIdAndIsEnable(userIdx, true).orElseThrow(UserNotFoundException::new);


        Fridge fridge = this.fridgeRepository.findByIdAndIsEnable(fridgeIdx, true).orElseThrow(FridgeNotFoundException::new);
        this.fridgeUserRepository.findByFridgeAndUserAndIsEnable(fridge, user, true).orElseThrow(FridgeUserNotFoundException::new);
        return RecipeFridgeFoodListsRes.toDto(this.fridgeFoodRepository.findByUserForFridgeRecipeFoodList(fridge));

    }

    @Transactional
    @Override
    public void notifyFridgeFood() {
        this.fridgeFoodRepository.findByActiveAndShelfLifeLimit3()
                .stream().forEach(food -> {
                    this.fridgeUserRepository.findByFridgeAndIsEnable(food.getFridge(), true)
                            .stream().forEach(user -> {
                                try {
                                    alarmService.sendShelfLifeAlarm(user.getUser(), food.getFridge().getFridgeName(), food.getFood().getFoodName());
                                } catch (IOException e) {
                                    throw new FridgeNameEmptyException(); //todo: 예외처리 바꾸기
                                }
                            });
                });

    }
}
