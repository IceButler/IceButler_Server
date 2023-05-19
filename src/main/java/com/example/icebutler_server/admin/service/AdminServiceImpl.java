package com.example.icebutler_server.admin.service;


import com.example.icebutler_server.admin.dto.condition.SearchCond;
import com.example.icebutler_server.admin.dto.request.LoginRequest;
import com.example.icebutler_server.admin.dto.request.WithDrawRequest;
import com.example.icebutler_server.admin.dto.response.LoginResponse;
import com.example.icebutler_server.admin.dto.response.LogoutResponse;
import com.example.icebutler_server.global.resolver.LoginStatus;
import com.example.icebutler_server.user.dto.response.MyProfileRes;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {
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
}
