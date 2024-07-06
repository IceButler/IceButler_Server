package com.example.icebutler_server.admin.controller;

import com.example.icebutler_server.admin.dto.request.*;
import com.example.icebutler_server.admin.dto.response.*;
import com.example.icebutler_server.admin.dto.response.AdminResponse;
import com.example.icebutler_server.admin.dto.response.LogoutResponse;
import com.example.icebutler_server.admin.dto.response.SearchFoodsResponse;
import com.example.icebutler_server.admin.dto.response.PostAdminRes;
import com.example.icebutler_server.admin.service.AdminService;
import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.food.entity.FoodCategory;
import com.example.icebutler_server.food.repository.FoodRepository;
import com.example.icebutler_server.global.dto.response.ResponseCustom;
import com.example.icebutler_server.global.resolver.*;
import com.example.icebutler_server.global.sqs.AmazonSQSSender;
import com.example.icebutler_server.global.sqs.FoodData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin")
@RestController
public class AdminController {

    private final AdminService adminService;

    private final AmazonSQSSender amazonSQSSender;

    private final FoodRepository foodRepository;

    @PostMapping("/join")
    public ResponseCustom<AdminResponse> join(@RequestBody JoinRequest request)
    {
        return ResponseCustom.success(adminService.join(request));
    }

    @PostMapping("/login")
    public ResponseCustom<PostAdminRes> login(@RequestBody LoginRequest request)
    {
        return ResponseCustom.success(adminService.login(request));
    }

    @Admin
    @PostMapping("/logout")
    public ResponseCustom<LogoutResponse> logout(@IsAdminLogin AdminLoginStatus loginStatus)
    {
        adminService.logout(loginStatus.getAdminIdx());
        return ResponseCustom.success();
    }

    @Admin
    @GetMapping("/users")
    public ResponseCustom<Page<UserResponse>> search(
            @IsAdminLogin AdminLoginStatus loginStatus,
            Pageable pageable,
            @RequestParam(defaultValue = "") String nickname,
            @RequestParam(defaultValue = "true") boolean active
    )
    {
        return ResponseCustom.success(adminService.search(pageable, nickname, active,loginStatus.getAdminIdx()));
    }

    @Admin
    @DeleteMapping("/users/{userIdx}")
    public ResponseCustom<Void> withdraw(
            @IsAdminLogin AdminLoginStatus loginStatus,
            @PathVariable Long userIdx,
            HttpServletRequest request
    )
    {
        adminService.withdraw(userIdx, loginStatus.getAdminIdx(), request.getHeader("Authorization"));
        return ResponseCustom.success();
    }

    // 식품조회
    @Admin
    @GetMapping("/foods")
    public ResponseCustom<Page<SearchFoodsResponse>> searchFoods(@RequestParam String cond, Pageable pageable
    ,@IsAdminLogin AdminLoginStatus loginStatus
    )
    {
        return ResponseCustom.success(adminService.searchFoods(cond, pageable,loginStatus.getAdminIdx()));
    }

    // 식품수정
    @Admin
    @PatchMapping("/foods/{foodIdx}")
    public ResponseCustom<Void> modifyFood(@PathVariable(name = "foodIdx") Long foodIdx,
                                           @RequestBody ModifyFoodRequest request
            ,@IsAdminLogin AdminLoginStatus loginStatus
    )
    {
        adminService.modifyFood(foodIdx, request,loginStatus.getAdminIdx());
        return ResponseCustom.success();
    }

    // 식품삭제
    @Admin
    @DeleteMapping("/foods/{foodIdx}")
    public ResponseCustom<Void> removeFoods(@PathVariable(name = "foodIdx") Long foodIdx
            ,@IsAdminLogin AdminLoginStatus loginStatus
    ) {
        adminService.removeFoods(foodIdx,loginStatus.getAdminIdx());
        return ResponseCustom.success();
    }

    @GetMapping("/sqs-test")
    public ResponseCustom<Void> getUserReportCheck() {
        Food testFood = Food.builder()
                .uuid(UUID.randomUUID())
                .foodName("testFoodName1")
                .foodCategory(FoodCategory.ETC)
                .foodImgKey("food/testFoodName1.img")
                .build();

        foodRepository.save(testFood);
        amazonSQSSender.sendMessage(FoodData.toDto(testFood));

        return ResponseCustom.success();
    }
}
