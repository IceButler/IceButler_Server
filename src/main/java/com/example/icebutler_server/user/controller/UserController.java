package com.example.icebutler_server.user.controller;

import com.example.icebutler_server.global.dto.response.ResponseCustom;
import com.example.icebutler_server.global.resolver.Auth;
import com.example.icebutler_server.global.resolver.IsLogin;
import com.example.icebutler_server.global.resolver.LoginStatus;
import com.example.icebutler_server.user.service.UserServiceImpl;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RequiredArgsConstructor
@RequestMapping("users")
@RestController
@Api(tags = "User 관련 API")
public class UserController {
    private final UserServiceImpl userService;

    //유저 탈퇴
    @Auth
    @DeleteMapping("/delete")
    public ResponseCustom<Boolean> deleteUser(
            @IsLogin LoginStatus loginStatus
    ){
        return ResponseCustom.OK(userService.deleteUser(loginStatus.getUserIdx()));
    }

    //유저 로그아
    @Auth
    @PostMapping("/logout")
    public ResponseCustom<Boolean> logout(
            @IsLogin LoginStatus loginStatus
    ){
        return ResponseCustom.OK(userService.logout(loginStatus.getUserIdx()));
    }

    // 마이페이지 조회
    @Auth
    @GetMapping("")
    public ResponseCustom<?> profile(
            @IsLogin LoginStatus loginStatus
    ){
        return ResponseCustom.OK(userService.myProfile(loginStatus.getUserIdx()));
    }



}
