package com.example.icebutler_server.admin.service;

import com.example.icebutler_server.admin.dto.condition.SearchCond;
import com.example.icebutler_server.admin.dto.request.JoinRequest;
import com.example.icebutler_server.admin.dto.request.LoginRequest;
import com.example.icebutler_server.admin.dto.request.WithDrawRequest;
import com.example.icebutler_server.admin.dto.response.AdminResponse;
import com.example.icebutler_server.admin.dto.response.LogoutResponse;
import com.example.icebutler_server.admin.dto.response.PostAdminRes;
import com.example.icebutler_server.user.dto.response.MyProfileRes;
import org.springframework.data.domain.Page;

public interface AdminService {
    AdminResponse join(JoinRequest request);
    PostAdminRes login(LoginRequest request);
    LogoutResponse logout(Long adminIdx);
    Page<MyProfileRes> search(SearchCond cond);
    void withdraw(WithDrawRequest request);
}
