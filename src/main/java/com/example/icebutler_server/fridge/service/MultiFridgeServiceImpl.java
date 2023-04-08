package com.example.icebutler_server.fridge.service;

import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.fridge.dto.fridge.request.FridgeFoodReq;
import com.example.icebutler_server.fridge.dto.fridge.request.FridgeModifyReq;
import com.example.icebutler_server.fridge.dto.fridge.response.FridgeFoodRes;
import com.example.icebutler_server.fridge.dto.fridge.response.FridgeFoodsRes;
import com.example.icebutler_server.fridge.dto.fridge.response.FridgeMainRes;
import com.example.icebutler_server.fridge.dto.multiFridge.assembler.MultiFridgeAssembler;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridge;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridgeUser;
import com.example.icebutler_server.fridge.exception.*;
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
    private final MultiFridgeUserRepository multiFridgeUserRepository;
    private final MultiFridgeRepository multiFridgeRepository;

    private final MultiFridgeAssembler multiFridgeAssembler;


    @Override
    public FridgeMainRes getFoods(Long fridgeIdx, Long userIdx, String category) {
        return null;
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
            List<User> newMembers = updateFridgeReq.getMembers().stream()
                    .map(m -> this.userRepository.findByUserIdxAndIsEnable(m.getUserIdx(), true).orElseThrow(UserNotFoundException::new)).collect(Collectors.toList());
            List<MultiFridgeUser> checkNewMember = this.multiFridgeAssembler.toUpdateFridgeMembers(newMembers, fridge.getMultiFridgeUsers());

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

    @Override
    public void addFridgeFood(FridgeFoodReq fridgeFoodReq, Long fridgeIdx, Long userIdx) {

    }
}
