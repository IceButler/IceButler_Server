package com.example.icebutler_server.admin.service;

import com.example.icebutler_server.admin.dto.request.JoinRequest;
import com.example.icebutler_server.admin.dto.request.LoginRequest;
import com.example.icebutler_server.admin.dto.request.ModifyFoodRequest;
import com.example.icebutler_server.admin.dto.response.AdminResponse;
import com.example.icebutler_server.admin.dto.response.PostAdminRes;
import com.example.icebutler_server.admin.dto.response.SearchFoodsResponse;
import com.example.icebutler_server.admin.dto.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminService {
    AdminResponse join(JoinRequest request);
    PostAdminRes login(LoginRequest request);
    void logout(Long adminId);
    Page<UserResponse> search(Pageable pageable, String nickname, boolean active, Long adminId);
    void withdraw(Long userId, Long adminId, String authorization);

    Page<SearchFoodsResponse> searchFoods(String cond, Pageable pageable, Long adminId);

    void modifyFood(Long foodId, ModifyFoodRequest request, Long adminId);

    void removeFoods(Long foodId, Long adminId);
}
