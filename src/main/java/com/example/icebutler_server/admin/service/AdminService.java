package com.example.icebutler_server.admin.service;

import com.example.icebutler_server.admin.dto.condition.SearchCond;
import com.example.icebutler_server.admin.dto.request.JoinRequest;
import com.example.icebutler_server.admin.dto.request.LoginRequest;
import com.example.icebutler_server.admin.dto.request.ModifyFoodRequest;
import com.example.icebutler_server.admin.dto.request.RemoveFoodsRequest;
import com.example.icebutler_server.admin.dto.request.WithDrawRequest;
import com.example.icebutler_server.admin.dto.response.AdminResponse;
import com.example.icebutler_server.admin.dto.response.LogoutResponse;
import com.example.icebutler_server.admin.dto.response.SearchFoodsResponse;
import com.example.icebutler_server.global.resolver.LoginStatus;
import com.example.icebutler_server.admin.dto.response.PostAdminRes;
import com.example.icebutler_server.admin.dto.response.UserResponse;
import com.example.icebutler_server.user.dto.response.MyProfileRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminService {
    AdminResponse join(JoinRequest request);
    PostAdminRes login(LoginRequest request);
    void logout(Long adminIdx);
    Page<UserResponse> search(Pageable pageable, String nickname, boolean active);
    void withdraw(WithDrawRequest request);

    Page<SearchFoodsResponse> searchFoods(String cond, Pageable pageable);

    void modifyFood(Long foodIdx, ModifyFoodRequest request);

    void removeFoods(RemoveFoodsRequest request);
}
