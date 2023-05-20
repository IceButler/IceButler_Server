package com.example.icebutler_server.admin.service;


import com.example.icebutler_server.admin.dto.assembler.AdminAssembler;
import com.example.icebutler_server.admin.dto.condition.SearchCond;
import com.example.icebutler_server.admin.dto.request.JoinRequest;
import com.example.icebutler_server.admin.dto.request.LoginRequest;
import com.example.icebutler_server.admin.dto.request.ModifyFoodRequest;
import com.example.icebutler_server.admin.dto.request.RemoveFoodsRequest;
import com.example.icebutler_server.admin.dto.request.WithDrawRequest;
import com.example.icebutler_server.admin.dto.response.AdminResponse;
import com.example.icebutler_server.admin.dto.response.LogoutResponse;
import com.example.icebutler_server.admin.dto.response.SearchFoodsResponse;
import com.example.icebutler_server.admin.exception.FoodNotFoundException;
import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.food.entity.FoodCategory;
import com.example.icebutler_server.food.exception.FoodCategoryNotFoundException;
import com.example.icebutler_server.food.exception.FoodNameNotFoundException;
import com.example.icebutler_server.food.repository.FoodRepository;
import com.example.icebutler_server.global.resolver.LoginStatus;
import com.example.icebutler_server.admin.dto.response.PostAdminRes;
import com.example.icebutler_server.admin.entity.Admin;
import com.example.icebutler_server.admin.exception.AdminNotFoundException;
import com.example.icebutler_server.admin.exception.PasswordNotMatchException;
import com.example.icebutler_server.admin.repository.AdminRepository;
import com.example.icebutler_server.global.util.RedisTemplateService;
import com.example.icebutler_server.global.util.TokenUtils;
import com.example.icebutler_server.user.dto.response.MyProfileRes;
import com.example.icebutler_server.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {

    private final FoodRepository foodRepository;
    private final AdminAssembler adminAssembler;
    private final TokenUtils tokenUtils;
    private final BCryptPasswordEncoder pwEncoder;
    private final AdminRepository adminRepository;
    private final RedisTemplateService redisTemplateService;

    @Override
    @Transactional
    public AdminResponse join(JoinRequest request)
    {
        Admin admin = adminRepository.save(request.toAdmin(pwEncoder.encode(request.getPassword())));
        return AdminResponse.toDto(admin);
    }

    @Override
    public PostAdminRes login(LoginRequest request)
    {
        Admin admin = adminRepository.findByEmail(request.getEmail()).orElseThrow(AdminNotFoundException::new);
        if(!pwEncoder.matches(request.getPassword(), admin.getPassword())) throw new PasswordNotMatchException();
        return PostAdminRes.toDto(tokenUtils.createToken(admin.getAdminIdx(), admin.getEmail()));
    }

    @Override
    public LogoutResponse logout(Long adminIdx) {
        Admin admin = adminRepository.findByAdminIdxAndIsEnable(adminIdx, true).orElseThrow(UserNotFoundException::new);
        redisTemplateService.deleteUserRefreshToken(admin.getAdminIdx());
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
