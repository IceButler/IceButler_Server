package com.example.icebutler_server.admin.service;

import com.example.icebutler_server.admin.dto.condition.SearchCond;
import com.example.icebutler_server.admin.dto.request.*;
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
    Page<UserResponse> search(Pageable pageable, String nickname, boolean active,Long adminIdx);
    void withdraw(Long userIdx,Long adminIdx, String authorization);

    Page<SearchFoodsResponse> searchFoods(String cond, Pageable pageable,Long adminIdx);

    void modifyFood(Long foodIdx, ModifyFoodRequest request,Long adminIdx);

    void removeFoods(Long foodIdx,Long adminIdx);
}
