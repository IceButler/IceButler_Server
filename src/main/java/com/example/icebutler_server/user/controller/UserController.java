package com.example.icebutler_server.user.controller;

import com.example.icebutler_server.global.dto.response.ResponseCustom;
import com.example.icebutler_server.global.resolver.Auth;
import com.example.icebutler_server.global.resolver.IsLogin;
import com.example.icebutler_server.user.dto.response.UserSearchRes;
import com.example.icebutler_server.user.service.UserService;
import com.example.icebutler_server.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.awt.print.Pageable;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * 유저 탈퇴
     */
    @Auth
    @DeleteMapping("/{reason}")
    public ResponseCustom<Boolean> deleteUser(
            @ApiIgnore @IsLogin Long userIdx,
            @PathVariable("reason") String reason
    ){
        return ResponseCustom.OK(userService.deleteUser(userIdx,reason));
    }
    /**
     * 유저 로그아웃
     */
    @Auth
    @DeleteMapping("/logout")
    public ResponseCustom<Boolean> logout(
            @ApiIgnore @IsLogin Long userIdx
    ){
        return ResponseCustom.OK(userService.logout(userIdx));
    }

    /**
     * 마이페이지 조회
     */
    @ResponseBody
    @GetMapping("/profile")
    public ResponseCustom<?> getProfile(
            @ApiIgnore @IsLogin Long userIdx
    ){
        return ResponseCustom.OK(userService.getProfile(userIdx));
    }

    /**
     * 유저 검색
     */
    @Auth
    @GetMapping("/{userIdx}")
    public ResponseCustom<UserSearchRes> searchUsers(
            @PathVariable("userIdx") Long userIdx
//            @ApiIgnore @IsLogin Long userId,
//            Pageable pageable
    ){
        return ResponseCustom.OK(userService.searchUsers(userIdx));
    }








}
