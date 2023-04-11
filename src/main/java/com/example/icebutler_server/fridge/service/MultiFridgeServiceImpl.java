package com.example.icebutler_server.fridge.service;

import com.example.icebutler_server.food.dto.assembler.FoodAssembler;
import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.food.entity.FoodCategory;
import com.example.icebutler_server.food.repository.FoodRepository;
import com.example.icebutler_server.fridge.dto.fridge.request.FridgeFoodReq;
import com.example.icebutler_server.fridge.dto.fridge.request.FridgeModifyReq;
import com.example.icebutler_server.fridge.dto.fridge.response.FridgeFoodRes;
import com.example.icebutler_server.fridge.dto.fridge.response.FridgeMainRes;
import com.example.icebutler_server.fridge.dto.fridge.response.FridgeUserMainRes;
import com.example.icebutler_server.fridge.dto.fridge.response.FridgeUsersRes;
import com.example.icebutler_server.fridge.dto.multiFridge.assembler.MultiFridgeAssembler;
import com.example.icebutler_server.fridge.dto.multiFridge.assembler.MultiFridgeFoodAssembler;
import com.example.icebutler_server.fridge.entity.fridge.Fridge;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridge;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridgeFood;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridgeUser;
import com.example.icebutler_server.fridge.exception.*;
import com.example.icebutler_server.fridge.repository.multiFridge.MultiFridgeFoodRepository;
import com.example.icebutler_server.fridge.repository.multiFridge.MultiFridgeRepository;
import com.example.icebutler_server.fridge.repository.multiFridge.MultiFridgeUserRepository;
import com.example.icebutler_server.global.dto.response.ResponseCustom;
import com.example.icebutler_server.global.entity.FridgeRole;
import com.example.icebutler_server.user.entity.User;
import com.example.icebutler_server.user.exception.*;
import com.example.icebutler_server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
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


    // 멀티 냉장고 전체 조회
    @Override
    public FridgeMainRes getFoods(Long multiFridgeIdx, Long userIdx, String category) {
        User user = this.userRepository.findByUserIdxAndIsEnable(userIdx, true).orElseThrow(UserNotFoundException::new);
        MultiFridge multiFridge = this.multiFridgeRepository.findByMultiFridgeIdxAndIsEnable(multiFridgeIdx, true).orElseThrow(FridgeNotFoundException::new);

        if(category == null){
            // 값이 없으면 전체 조회
            return FridgeMainRes.toMultiDto(this.multiFridgeFoodRepository.findByIsEnableOrderByShelfLife(true));
        }else{
            // 값이 있으면 특정 값을 불러온 조회
            return FridgeMainRes.toMultiDto(this.multiFridgeFoodRepository.findByFood_FoodCategoryAndIsEnableOrderByShelfLife(FoodCategory.getFoodCategoryByName(category), true));

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
    public ResponseCustom<Long> removeFridge(Long fridgeIdx, Long userIdx) {
        return null;
    }


    @Override
    public List<Food> findFoodByName(Long fridgeIdx, Long ownerIdx, String foodName){
        return null;
    }

    @Override
    public FridgeFoodRes getFridgeFood(Long fridgeIdx, Long fridgeFoodIdx, Long userIdx) {
        return null;
    }

    // 냉장고 내 식품 추가
    @Transactional
    @Override
    public void addFridgeFood(FridgeFoodReq fridgeFoodReq, Long fridgeIdx, Long userIdx) {
        User user = this.userRepository.findByUserIdxAndIsEnable(userIdx, true).orElseThrow(UserNotFoundException::new);
        MultiFridge fridge = this.multiFridgeRepository.findByMultiFridgeIdxAndIsEnable(fridgeIdx, true).orElseThrow(FridgeNotFoundException::new);
        this.multiFridgeUserRepository.findByMultiFridgeAndUserAndIsEnable(fridge, user, true).orElseThrow(FridgeUserNotFoundException::new);


        User owner = this.userRepository.findByUserIdxAndIsEnable(fridgeFoodReq.getOwnerIdx(), true).orElseThrow(UserNotFoundException::new);
        this.multiFridgeUserRepository.findByMultiFridgeAndUserAndIsEnable(fridge, user, true).orElseThrow(FridgeUserNotFoundException::new);

        Food food = this.foodRepository.findByFoodName(fridgeFoodReq.getFoodName());
        if(food == null) food = foodRepository.save(this.foodAssembler.toEntity(fridgeFoodReq));
        this.multiFridgeFoodRepository.save(this.multiFridgeFoodAssembler.toEntity(owner, fridge, food, fridgeFoodReq));
    }

    // 냉장고 식품 수정
    @Transactional
    @Override
    public void modifyFridgeFood(Long fridgeIdx, Long fridgeFoodIdx, FridgeFoodReq fridgeFoodReq, Long userIdx) {
        User user = this.userRepository.findByUserIdxAndIsEnable(userIdx, true).orElseThrow(UserNotFoundException::new);
        MultiFridge fridge = this.multiFridgeRepository.findByMultiFridgeIdxAndIsEnable(fridgeIdx, true).orElseThrow(FridgeNotFoundException::new);
        this.multiFridgeUserRepository.findByMultiFridgeAndUserAndIsEnable(fridge, user, true).orElseThrow(FridgeUserNotFoundException::new);
        MultiFridgeFood modifyMultiFridgeFood = this.multiFridgeFoodRepository.findByMultiFridgeFoodIdxAndOwnerAndMultiFridgeAndIsEnable(fridgeFoodIdx, user, fridge, true).orElseThrow(FridgeFoodNotFoundException::new);

        // todo: foodName은 isEnable을 확인할 필요가 없나ㅇ,,
        if(!modifyMultiFridgeFood.getFood().getFoodName().equals(fridgeFoodReq.getFoodName())) {
            Food food = this.foodRepository.findByFoodName(fridgeFoodReq.getFoodName());
            if(food == null) food = foodRepository.save(this.foodAssembler.toEntity(fridgeFoodReq));
            this.multiFridgeFoodAssembler.toUpdateMultiFridgeFoodInfo(modifyMultiFridgeFood, food);
        }

        this.multiFridgeFoodAssembler.toUpdateBasicMultiFridgeFoodInfo(modifyMultiFridgeFood, fridgeFoodReq);

        // todo: controller domain 별로 나뉘어지면 exception 변경 예정 -> 충돌 땜시
        if(!modifyMultiFridgeFood.getOwner().getUserIdx().equals(fridgeFoodReq.getOwnerIdx())){
            User newOwner = this.userRepository.findByUserIdxAndIsEnable(fridgeFoodReq.getOwnerIdx(), true).orElseThrow(UserNotFoundException::new);
            this.multiFridgeUserRepository.findByMultiFridgeAndUserAndIsEnable(fridge, newOwner, true).orElseThrow(FridgeUserNotFoundException::new);
            this.multiFridgeFoodAssembler.toUpdateMultiFridgeFoodOwner(modifyMultiFridgeFood, newOwner);
        }
    }

    @Override
    public FridgeUserMainRes searchMembers(Long fridgeIdx, Long userIdx) {
        User user=this.userRepository.findByUserIdxAndIsEnable(userIdx,true).orElseThrow(UserNotFoundException::new);
        MultiFridge fridge=this.multiFridgeRepository.findByMultiFridgeIdxAndIsEnable(fridgeIdx,true).orElseThrow(FridgeNotFoundException::new);

        return new FridgeUserMainRes(this.multiFridgeUserRepository.findByMultiFridge(user).stream()
                .map(ff -> new FridgeUsersRes(ff.getUser().getUserIdx(), ff.getUser().getNickname(),ff.getUser().getProfileImage())).collect(Collectors.toList()));
    }
}
