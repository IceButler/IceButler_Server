package com.example.icebutler_server.admin.controller;

import com.example.icebutler_server.admin.dto.condition.SearchCond;
import com.example.icebutler_server.admin.dto.request.JoinRequest;
import com.example.icebutler_server.admin.dto.request.LoginRequest;
import com.example.icebutler_server.admin.dto.request.WithDrawRequest;
import com.example.icebutler_server.admin.dto.response.AdminResponse;
import com.example.icebutler_server.admin.dto.response.LoginResponse;
import com.example.icebutler_server.admin.dto.response.LogoutResponse;
import com.example.icebutler_server.admin.dto.response.PostAdminRes;
import com.example.icebutler_server.admin.service.AdminService;
import com.example.icebutler_server.global.dto.response.ResponseCustom;
import com.example.icebutler_server.global.resolver.*;
import com.example.icebutler_server.user.dto.response.MyProfileRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin")
@RestController
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/join")
    public ResponseCustom<AdminResponse> join(@RequestBody JoinRequest request)
    {
        return ResponseCustom.OK(adminService.join(request));
    }
    @PostMapping("/login")
    public ResponseCustom<PostAdminRes> login(@RequestBody LoginRequest request)
    {
        return ResponseCustom.OK(adminService.login(request));
    }
    @Admin
    @PostMapping("/logout")
    public ResponseCustom<LogoutResponse> logout(@IsAdminLogin AdminLoginStatus loginStatus)
    {
        return ResponseCustom.OK(adminService.logout(loginStatus.getAdminIdx()));
    }
    @Admin
    @GetMapping("/users")
    public ResponseCustom<Page<MyProfileRes>> search(
            @IsAdminLogin AdminLoginStatus loginStatus,
            SearchCond cond
    )
    {
        return ResponseCustom.OK(adminService.search(cond));
    }
    @Admin
    @DeleteMapping("/users")
    public ResponseCustom<Void> withdraw(
            @IsAdminLogin AdminLoginStatus loginStatus,
            @RequestBody WithDrawRequest request
    )
    {
        adminService.withdraw(request);
        return ResponseCustom.OK();
    }
}
