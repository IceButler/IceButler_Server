package com.example.icebutler_server.admin.service;

import com.example.icebutler_server.admin.dto.condition.SearchCond;
import com.example.icebutler_server.admin.dto.request.LoginRequest;
import com.example.icebutler_server.admin.dto.request.WithDrawRequest;
import com.example.icebutler_server.admin.dto.response.LoginResponse;
import com.example.icebutler_server.admin.dto.response.LogoutResponse;
import com.example.icebutler_server.global.dto.response.ResponseCustom;
import com.example.icebutler_server.global.resolver.LoginStatus;
import com.example.icebutler_server.user.dto.response.MyProfileRes;
import org.springframework.data.domain.Page;

public interface AdminService {
    LoginResponse login(LoginRequest request);

    LogoutResponse logout(LoginStatus loginStatus);

    Page<MyProfileRes> search(SearchCond cond);

    void withdraw(WithDrawRequest request);
}