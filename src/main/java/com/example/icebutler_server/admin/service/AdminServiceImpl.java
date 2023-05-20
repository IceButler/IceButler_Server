package com.example.icebutler_server.admin.service;


import com.example.icebutler_server.admin.dto.assembler.AdminAssembler;
import com.example.icebutler_server.admin.dto.condition.SearchCond;
import com.example.icebutler_server.admin.dto.request.LoginRequest;
import com.example.icebutler_server.admin.dto.request.ModifyFoodRequest;
import com.example.icebutler_server.admin.dto.request.RemoveFoodsRequest;
import com.example.icebutler_server.admin.dto.request.WithDrawRequest;
import com.example.icebutler_server.admin.dto.response.LoginResponse;
import com.example.icebutler_server.admin.dto.response.LogoutResponse;
import com.example.icebutler_server.admin.dto.response.SearchFoodsResponse;
import com.example.icebutler_server.admin.exception.FoodNotFoundException;
import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.food.entity.FoodCategory;
import com.example.icebutler_server.food.exception.FoodCategoryNotFoundException;
import com.example.icebutler_server.food.exception.FoodNameNotFoundException;
import com.example.icebutler_server.food.repository.FoodRepository;
import com.example.icebutler_server.global.resolver.LoginStatus;
import com.example.icebutler_server.user.dto.response.MyProfileRes;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {

    private final FoodRepository foodRepository;
    private final AdminAssembler adminAssembler;

    @Override
    public LoginResponse login(LoginRequest request) {
        return null;
    }

    @Override
    public LogoutResponse logout(LoginStatus loginStatus) {
        return null;
    }

    @Override
    public Page<MyProfileRes> search(SearchCond cond) {
        return null;
    }

    @Override
    public void withdraw(WithDrawRequest request) {

    }

    @Override
    public Page<SearchFoodsResponse> searchFoods(SearchCond cond, Pageable pageable) {
        Page<SearchFoodsResponse> searchFoods;

        if (StringUtils.hasText(cond.getCond())){
            Page<Food> searchFood = foodRepository.findByFoodNameContainsAndIsEnable(cond.getCond(), true, pageable);
            searchFoods = searchFood.map(SearchFoodsResponse::toDto);
            return searchFoods;
        }

        Page<Food> all = foodRepository.findAll(pageable);
        searchFoods = all.map(SearchFoodsResponse::toDto);
        return searchFoods;
    }

    @Override
    @Transactional
    public void modifyFood(Long foodIdx, ModifyFoodRequest request) {
        Food food = foodRepository.findByFoodIdxAndIsEnable(foodIdx, true).orElseThrow(FoodNotFoundException::new);
        foodRepository.save(adminAssembler.toUpdateFoodInfo(food, request));
    }

    @Override
    @Transactional
    public void removeFoods(RemoveFoodsRequest request) {
        List<Food> removeFoods = request.getRemoveFoods().stream().map(m -> foodRepository.findByFoodIdxAndIsEnable(m.getFoodIdx(), true).orElseThrow(FoodNotFoundException::new)).collect(Collectors.toList());
        foodRepository.deleteAll(removeFoods);
    }

}
