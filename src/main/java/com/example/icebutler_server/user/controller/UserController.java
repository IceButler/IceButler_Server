package com.example.icebutler_server.user.controller;

import com.example.icebutler_server.global.dto.response.ResponseCustom;
import com.example.icebutler_server.global.resolver.Auth;
import com.example.icebutler_server.global.resolver.IsLogin;
import com.example.icebutler_server.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@RestController
@RequestMapping(value = "users")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userServiceImpl;

    // 회원 탈퇴
    @Auth
    @DeleteMapping("/{reason}")
    public ResponseCustom<Boolean> deleteUser(
            @ApiIgnore @IsLogin Long userId,
            @PathVariable("reason") String reason
    ){
        return ResponseCustom.OK(userServiceImpl.deleteUser(userId,reason));
    }






}
