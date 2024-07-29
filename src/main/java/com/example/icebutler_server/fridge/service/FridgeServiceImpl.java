package com.example.icebutler_server.fridge.service;

import com.example.icebutler_server.alarm.service.NotificationServiceImpl;
import com.example.icebutler_server.cart.entity.Cart;
import com.example.icebutler_server.cart.repository.CartRepository;
import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.food.entity.FoodCategory;
import com.example.icebutler_server.food.entity.FoodDeleteStatus;
import com.example.icebutler_server.food.repository.FoodRepository;
import com.example.icebutler_server.global.util.FridgeUtils;
import com.example.icebutler_server.fridge.dto.request.*;
import com.example.icebutler_server.fridge.dto.response.*;
import com.example.icebutler_server.fridge.entity.Fridge;
import com.example.icebutler_server.fridge.entity.FridgeFood;
import com.example.icebutler_server.fridge.entity.FridgeUser;
import com.example.icebutler_server.fridge.repository.FridgeFood.FridgeFoodRepository;
import com.example.icebutler_server.fridge.repository.FridgeRepository;
import com.example.icebutler_server.fridge.repository.FridgeUserRepository;
import com.example.icebutler_server.global.entity.FridgeRole;
import com.example.icebutler_server.global.exception.BaseException;
import com.example.icebutler_server.global.sqs.AmazonSQSSender;
import com.example.icebutler_server.global.sqs.FoodData;
import com.example.icebutler_server.global.util.AwsS3ImageUrlUtil;
import com.example.icebutler_server.user.entity.User;
import com.example.icebutler_server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.icebutler_server.global.exception.ReturnCode.*;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class FridgeServiceImpl implements FridgeService {

    private final FridgeRepository fridgeRepository;
    private final FridgeUserRepository fridgeUserRepository;
    private final UserRepository userRepository;
    private final FridgeFoodRepository fridgeFoodRepository;
    private final FoodRepository foodRepository;
    private final CartRepository cartRepository;

    private final AmazonSQSSender amazonSQSSender;
    private final NotificationServiceImpl alarmService;

    @Override
    public FridgeMainRes getFoods(Long fridgeId, Long userId, String category) {
        User user = this.userRepository.findByIdAndIsEnable(userId, true).orElseThrow(() -> new BaseException(NOT_FOUND_USER));
        Fridge fridge = this.fridgeRepository.findByIdAndIsEnable(fridgeId, true).orElseThrow(() -> new BaseException(NOT_FOUND_FRIDGE));

        if (category == null) {
            // 값이 없으면 전체 조회
            return FridgeMainRes.toFridgeDto(this.fridgeFoodRepository.findByFridgeAndIsEnableOrderByShelfLife(fridge, true));
        } else {
            // 값이 있으면 특정 값을 불러온 조회
            return FridgeMainRes.toFridgeDto(this.fridgeFoodRepository.findByFridgeAndFood_FoodCategoryAndIsEnableOrderByShelfLife(fridge, FoodCategory.getFoodCategoryByName(category), true));
        }
    }

    @Override
    @Transactional
    public Long registerFridge(FridgeRegisterReq registerFridgeReq, Long ownerId) {
        if (!StringUtils.hasText(registerFridgeReq.getFridgeName())) throw new BaseException(INVALID_PARAM);
        Fridge fridge = Fridge.toEntity(registerFridgeReq);
        fridgeRepository.save(fridge);

        List<FridgeUser> fridgeUsers = new ArrayList<>();
        List<User> users = registerFridgeReq.getMembers().stream().map(m -> userRepository.findByIdAndIsEnable(m.getUserId(), true).orElseThrow(() -> new BaseException(NOT_FOUND_USER))).collect(Collectors.toList());
        User owner = userRepository.findByIdAndIsEnable(ownerId, true).orElseThrow(() -> new BaseException(NOT_FOUND_USER));

        // fridge - fridgeUser  연관관계 추가
        for (User user : users) {
            fridgeUsers.add(FridgeUser.builder().fridge(fridge).user(user).role(FridgeRole.MEMBER).build());
        }
        fridgeUsers.add(FridgeUser.builder().fridge(fridge).user(owner).role(FridgeRole.OWNER).build());
        fridgeUserRepository.saveAll(fridgeUsers);

        // fridge - cart 연관관계 추가
        cartRepository.save(Cart.toEntity(fridge));

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
    public void modifyFridge(Long fridgeId, FridgeModifyReq updateFridgeReq, Long userId) {
        User user = this.userRepository.findByIdAndIsEnable(userId, true).orElseThrow(() -> new BaseException(NOT_FOUND_USER));
        Fridge fridge = this.fridgeRepository.findByIdAndIsEnable(fridgeId, true).orElseThrow(() -> new BaseException(NOT_FOUND_FRIDGE));
        FridgeUser owner = this.fridgeUserRepository.findByFridgeAndUserAndRoleAndIsEnable(fridge, user, FridgeRole.OWNER, true).orElseThrow(() -> new BaseException(NO_PERMISSION));

        // 오너 업데이트
        if (!owner.getUser().getId().equals(updateFridgeReq.getNewOwnerId())) {
            FridgeUser newOwner = this.fridgeUserRepository.findByFridgeAndUser_IdAndRoleAndIsEnableAndUser_IsEnable(fridge, updateFridgeReq.getNewOwnerId(), FridgeRole.MEMBER, true, true).orElseThrow(() -> new BaseException(NOT_FOUND_FRIDGE_USER));
            toUpdateFridgeOwner(owner, newOwner);
        }

        // 냉장고 정보 (이름, 설명) 업데이트
        if (!StringUtils.hasText(updateFridgeReq.getFridgeName())) throw new BaseException(INVALID_PARAM);

        // 멤버 업데이트
        if (updateFridgeReq.getMembers() != null) {
            List<FridgeUser> members = this.fridgeUserRepository.findByFridgeAndIsEnable(fridge, true);
            List<User> newMembers = updateFridgeReq.getMembers().stream()
                    .map(m -> this.userRepository.findByIdAndIsEnable(m.getUserId(), true).orElseThrow(() -> new BaseException(NOT_FOUND_USER))).collect(Collectors.toList());
            UpdateMembersRes updateMembers = toUpdateFridgeMembers(newMembers, members);

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

    private UpdateMembersRes toUpdateFridgeMembers(List<User> newMembers, List<FridgeUser> fridgeUsers) {
        for (FridgeUser member : fridgeUsers) {
            member.setIsEnable(false);
        }
        List<FridgeUser> checkNewMember = new ArrayList<>();
        List<FridgeUser> withDrawMember = new ArrayList<>();

        for (User user : newMembers) {
            boolean hasMember = false;

            for (FridgeUser members : fridgeUsers) {
                if (user.equals(members.getUser())) {
                    members.setIsEnable(true);
                    hasMember = true;
                }
                if (members.getRole().equals(FridgeRole.OWNER)) {
                    members.setIsEnable(true);
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
        for (FridgeUser f : fridgeUsers) {
            if (!f.getIsEnable()) withDrawMember.add(f);
        }
        return UpdateMembersRes.toDto(withDrawMember, checkNewMember);
    }

    private void toUpdateFridgeOwner(FridgeUser owner, FridgeUser newOwner) {
        owner.changeFridgeMember(owner.getUser());
        newOwner.changeFridgeOwner(newOwner.getUser());
    }

    // 냉장고 자체 삭제
    @Transactional
    public Long removeFridge(Long fridgeId, Long userId) {
        User user = userRepository.findByIdAndIsEnable(userId, true).orElseThrow(() -> new BaseException(NOT_FOUND_USER));
        Fridge fridge = fridgeRepository.findByIdAndIsEnable(fridgeId, true).orElseThrow(() -> new BaseException(NOT_FOUND_FRIDGE));
        FridgeUser owner = (FridgeUser) fridgeUserRepository.findByUserAndFridgeAndIsEnable(user, fridge, true).orElseThrow(() -> new BaseException(NO_PERMISSION));
        List<FridgeUser> fridgeUsers = fridgeUserRepository.findByFridgeAndIsEnable(fridge, true);
        List<FridgeFood> fridgeFoods = fridgeFoodRepository.findByFridgeAndIsEnableOrderByShelfLife(fridge, true);

        if (owner.getRole() != FridgeRole.OWNER) throw new BaseException(NO_PERMISSION);
        if (fridgeUsers.size() > 1) throw new BaseException(STILL_MEMBER_EXIST);

        fridgeUsers.forEach(FridgeUser::remove);
//        fridgeFoods.forEach(FridgeFood::remove);
        fridge.remove();
        fridgeFoodRepository.removeFridgeFoodByFridge(false, fridge);

        return fridge.getId();
    }

    // 냉장고 개별
    @Override
    @Transactional
    public Long removeFridgeUser(Long fridgeId, Long userId) {
        User user = userRepository.findByIdAndIsEnable(userId, true).orElseThrow(() -> new BaseException(NOT_FOUND_USER));
        Fridge fridge = fridgeRepository.findByIdAndIsEnable(fridgeId, true).orElseThrow(() -> new BaseException(NOT_FOUND_FRIDGE));
        FridgeUser fridgeUser = (FridgeUser) fridgeUserRepository.findByUserAndFridgeAndIsEnable(user, fridge, true).orElseThrow(() -> new BaseException(NOT_FOUND_FRIDGE_USER));

        if (fridgeUser.getRole() == FridgeRole.OWNER) throw new BaseException(NO_PERMISSION);
        fridgeUser.remove();

        return fridge.getId();
    }

    @Override
    public List<FridgeFoodsRes> searchFridgeFood(Long fridgeId, Long ownerId, String keyword) {
        Fridge fridge = fridgeRepository.findByIdAndIsEnable(fridgeId, true).orElseThrow(() -> new BaseException(NOT_FOUND_FRIDGE));
        List<FridgeFood> searchFoods = fridgeFoodRepository.findByFoodDetailNameContainingAndFridgeAndIsEnable(keyword, fridge, true);
        return searchFoods.stream().map(FridgeFoodsRes::toDto).collect(Collectors.toList());
    }

    @Override
    public FridgeFoodRes getFridgeFood(Long fridgeId, Long fridgeFoodId, Long userId) {
        User user = userRepository.findByIdAndIsEnable(userId, true).orElseThrow(() -> new BaseException(NOT_FOUND_USER));
        Fridge fridge = fridgeRepository.findByIdAndIsEnable(fridgeId, true).orElseThrow(() -> new BaseException(NOT_FOUND_FRIDGE));
        fridgeUserRepository.findByUserAndFridgeAndIsEnable(user, fridge, true).orElseThrow(() -> new BaseException(NO_PERMISSION));
        FridgeFood fridgeFood = fridgeFoodRepository.findByIdAndFridgeAndIsEnable(fridgeFoodId, fridge, true).orElseThrow(() -> new BaseException(NOT_FOUND_FRIDGE_FOOD));

        return FridgeFoodRes.toDto(fridgeFood);
    }

    @Override
    @Transactional
    public void addFridgeFood(FridgeFoodsReq fridgeFoodsReq, Long fridgeId, Long userId) {
        User user = userRepository.findByIdAndIsEnable(userId, true).orElseThrow(() -> new BaseException(NOT_FOUND_USER));
        Fridge fridge = fridgeRepository.findByIdAndIsEnable(fridgeId, true).orElseThrow(() -> new BaseException(NOT_FOUND_FRIDGE));
        fridgeUserRepository.findByUserAndFridgeAndIsEnable(user, fridge, true).orElseThrow(() -> new BaseException(NO_PERMISSION));

        List<FridgeFood> fridgeFoods = new ArrayList<>();
        for (FridgeFoodReq fridgeFoodReq : fridgeFoodsReq.getFridgeFoods()) {
            User owner = null;
            if (fridgeFoodReq.getOwnerId() != null) {
                owner = userRepository.findByIdAndIsEnable(fridgeFoodReq.getOwnerId(), true).orElseThrow(() -> new BaseException(NOT_FOUND_USER));
                fridgeUserRepository.findByUserAndFridgeAndIsEnable(owner, fridge, true).orElseThrow(() -> new BaseException(NOT_FOUND_FRIDGE_USER));
            }
            Food food = foodRepository.findByFoodName(fridgeFoodReq.getFoodName())
                    .orElseGet(() -> {
                        Food save = foodRepository.save(Food.toEntity(fridgeFoodReq));
//                        amazonSQSSender.sendMessage(FoodData.toDto(save));
                        return save;
                    });

            fridgeFoods.add(FridgeFood.toEntity(owner, fridge, food, fridgeFoodReq));
        }
        fridgeFoodRepository.saveAll(fridgeFoods);
    }

    @Override
    @Transactional
    public void modifyFridgeFood(Long fridgeId, Long fridgeFoodId, FridgeFoodReq fridgeFoodReq, Long userId) {
        User user = this.userRepository.findByIdAndIsEnable(userId, true)
                .orElseThrow(() -> new BaseException(NOT_FOUND_USER));
        Fridge fridge = this.fridgeRepository.findByIdAndIsEnable(fridgeId, true)
                .orElseThrow(() -> new BaseException(NOT_FOUND_FRIDGE));
        this.fridgeUserRepository.findByFridgeAndUserAndIsEnable(fridge, user, true)
                .orElseThrow(() -> new BaseException(NO_PERMISSION));
        FridgeFood modifyFridgeFood = this.fridgeFoodRepository.findByIdAndFridgeAndIsEnable(fridgeFoodId, fridge, true)
                .orElseThrow(() -> new BaseException(NOT_FOUND_FRIDGE_FOOD));

        if (!modifyFridgeFood.getFood().getFoodName().equals(fridgeFoodReq.getFoodName())) {
            Food food = this.foodRepository.findByFoodName(fridgeFoodReq.getFoodName())
                    .orElseGet(() -> {
                        Food save = foodRepository.save(Food.toEntity(fridgeFoodReq));
//                        amazonSQSSender.sendMessage(FoodData.toDto(save));
                        return save;
                    });
            modifyFridgeFood.updateFridgeFoodInfo(food);
        }

        modifyFridgeFood.updateFridgeFoodInfo(
                fridgeFoodReq.getFoodDetailName(),
                fridgeFoodReq.getMemo(),
                LocalDate.parse(fridgeFoodReq.getShelfLife()),
                fridgeFoodReq.getImgKey()
        );

        if (fridgeFoodReq.getOwnerId() == null)
            modifyFridgeFood.updateFridgeFoodOwner(null);
        else {
            User newOwner = this.userRepository.findByIdAndIsEnable(fridgeFoodReq.getOwnerId(), true)
                    .orElseThrow(() -> new BaseException(NOT_FOUND_USER));
            this.fridgeUserRepository.findByFridgeAndUserAndIsEnable(fridge, newOwner, true)
                    .orElseThrow(() -> new BaseException(NOT_FOUND_FRIDGE_USER));
            if (!newOwner.equals(modifyFridgeFood.getOwner()))
                modifyFridgeFood.updateFridgeFoodOwner(newOwner);
        }
    }

    @Override
    @Transactional
    public void deleteFridgeFood(DeleteFridgeFoodsReq deleteFridgeFoodsReq, String type, Long fridgeId, Long userId) {
        FoodDeleteStatus deleteStatus = FoodDeleteStatus.getFoodDeleteStatusByName(type);
        User user = this.userRepository.findByIdAndIsEnable(userId, true)
                .orElseThrow(() -> new BaseException(NOT_FOUND_USER));
        Fridge fridge = this.fridgeRepository.findByIdAndIsEnable(fridgeId, true)
                .orElseThrow(() -> new BaseException(NOT_FOUND_FRIDGE));
        this.fridgeUserRepository.findByFridgeAndUserAndIsEnable(fridge, user, true)
                .orElseThrow(() -> new BaseException(NOT_FOUND_FRIDGE_USER));

        List<FridgeFood> deleteFridgeFoods = deleteFridgeFoodsReq.getDeleteFoods().stream()
                .map(foodId -> this.fridgeFoodRepository.findByIdAndFridgeAndIsEnable(foodId, fridge, true)
                        .orElseThrow(() -> new BaseException(NOT_FOUND_FRIDGE_FOOD)))
                .collect(Collectors.toList());

        deleteFridgeFoods.forEach(food -> food.removeWithStatus(deleteStatus));
    }

    @Override
    //냉장고 내 유저 조회
    public FridgeUserMainRes searchMembers(Long fridgeId, Long userId) {
        Fridge fridge = fridgeRepository.findByIdAndIsEnable(fridgeId, true)
                .orElseThrow(() -> new BaseException(NOT_FOUND_FRIDGE));
        return FridgeUserMainRes.doDto(fridgeUserRepository.findByFridgeAndIsEnable(fridge, true));
    }

    @Override
    public FridgeFoodsStatistics getFridgeFoodStatistics(Long fridgeId, String deleteCategory, Long userId, Integer year, Integer month) {
        User user = this.userRepository.findByIdAndIsEnable(userId, true).orElseThrow(() -> new BaseException(NOT_FOUND_USER));
        Fridge fridge = this.fridgeRepository.findByIdAndIsEnable(fridgeId, true).orElseThrow(() -> new BaseException(NOT_FOUND_FRIDGE));
        this.fridgeUserRepository.findByFridgeAndUserAndIsEnable(fridge, user, true).orElseThrow(() -> new BaseException(NO_PERMISSION));

        Map<FoodCategory, Long> deleteStatusList = new HashMap<>();

        for (FoodCategory category : FoodCategory.values()) {
            Long foodSize = this.fridgeFoodRepository.findByDeleteCategoryForStatistics(FoodDeleteStatus.getFoodDeleteStatusByName(deleteCategory), fridge, category, year, month);
            deleteStatusList.put(category, foodSize);
        }

        return toFoodStatisticsByDeleteStatus(deleteStatusList);
    }

    private FridgeFoodsStatistics toFoodStatisticsByDeleteStatus(Map<FoodCategory, Long> deleteStatusList) {
        int sum = 0;
        for (Long value : deleteStatusList.values()) {
            sum += value.intValue();
        }
        List<FridgeFoodStatistics> foodStatisticsList = new ArrayList<>();

        for (Map.Entry<FoodCategory, Long> deleteStatus : deleteStatusList.entrySet()) {
            foodStatisticsList.add(new FridgeFoodStatistics(deleteStatus.getKey().getName(), AwsS3ImageUrlUtil.toUrl(deleteStatus.getKey().getImage()), FridgeUtils.calPercentage(deleteStatus.getValue().intValue(), sum), deleteStatus.getValue().intValue()));
        }
        // sorting
        foodStatisticsList.sort((fs1, fs2) -> (fs2.getCount() - fs1.getCount()));
        return FridgeFoodsStatistics.toDto(foodStatisticsList);
    }

    public SelectFridgesMainRes selectFridges(Long userId) {
        User user = userRepository.findByIdAndIsEnable(userId, true).orElseThrow(() -> new BaseException(NOT_FOUND_USER));
        return SelectFridgesMainRes.toDto(fridgeUserRepository.findByUserAndIsEnable(user, true));
    }

    public GetFridgesMainRes myFridge(Long userId) {
        User user = userRepository.findByIdAndIsEnable(userId, true).orElseThrow(() -> new BaseException(NOT_FOUND_USER));

        // 가정용 냉장고 조회
        List<FridgeUser> fridgeUsers = fridgeUserRepository.findByUserAndIsEnable(user, true);
        List<Fridge> fridges = fridgeUsers.stream().map(m -> fridgeRepository.findByIdAndIsEnable(m.getFridge().getId(), true).orElseThrow(() -> new BaseException(NOT_FOUND_FRIDGE))).collect(Collectors.toList());
        List<List<FridgeUser>> fridgeUserListList = fridges.stream().map(m -> fridgeUserRepository.findByFridgeAndIsEnableOrderByRoleDesc(m, true)).collect(Collectors.toList());

        return GetFridgesMainRes.toDto(fridgeUserListList, userId);

    }

    //  사용자가 속한 가정용/공용 냉장고 food list
    public RecipeFridgeFoodListsRes getFridgeUserFoodList(Long fridgeId, Long userId) {
        User user = userRepository.findByIdAndIsEnable(userId, true).orElseThrow(() -> new BaseException(NOT_FOUND_USER));


        Fridge fridge = this.fridgeRepository.findByIdAndIsEnable(fridgeId, true).orElseThrow(() -> new BaseException(NOT_FOUND_FRIDGE));
        this.fridgeUserRepository.findByFridgeAndUserAndIsEnable(fridge, user, true).orElseThrow(() -> new BaseException(NOT_FOUND_FRIDGE_USER));
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
                                    throw new BaseException(INTERNAL_SERVER_ERROR); //todo: 예외처리 바꾸기
                                }
                            });
                });

    }
}
