package com.example.icebutler_server.admin.controller;

import com.example.icebutler_server.admin.dto.condition.SearchCond;
import com.example.icebutler_server.admin.dto.request.LoginRequest;
import com.example.icebutler_server.admin.dto.request.WithDrawRequest;
import com.example.icebutler_server.admin.dto.response.LoginResponse;
import com.example.icebutler_server.admin.dto.response.LogoutResponse;
import com.example.icebutler_server.admin.service.AdminService;
import com.example.icebutler_server.global.dto.response.ResponseCustom;
import com.example.icebutler_server.global.resolver.Admin;
import com.example.icebutler_server.global.resolver.Auth;
import com.example.icebutler_server.global.resolver.IsLogin;
import com.example.icebutler_server.global.resolver.LoginStatus;
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

    @PostMapping("/login")
    public ResponseCustom<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseCustom.OK(adminService.login(request));
    }
    @Admin
    @PostMapping("/logout")
    public ResponseCustom<LogoutResponse> logout(@IsLogin LoginStatus loginStatus) {
        return ResponseCustom.OK(adminService.logout(loginStatus));
    }

    @Admin
    @GetMapping("/users")
    public ResponseCustom<Page<MyProfileRes>> search(SearchCond cond) {
        return ResponseCustom.OK(adminService.search(cond));
    }

    @Admin
    @DeleteMapping("/users")
    public ResponseCustom<Void> withdraw(@RequestBody WithDrawRequest request) {
        adminService.withdraw(request);
        return ResponseCustom.OK();
    }
}
