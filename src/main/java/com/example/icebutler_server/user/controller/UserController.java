package com.example.icebutler_server.user.controller;
import com.example.icebutler_server.global.dto.response.ResponseCustom;
import com.example.icebutler_server.global.resolver.Auth;
import com.example.icebutler_server.global.resolver.IsLogin;


import com.example.icebutler_server.global.resolver.LoginStatus;
import com.example.icebutler_server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@RestController
@RequestMapping(value = "users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

//    @Auth
//    @DeleteMapping("/delete")
//    public ResponseCustom<Boolean> deleteUser(
//            @ApiIgnore @IsLogin Long userIdx
//    ){
//        return ResponseCustom.OK(userService.deleteUser(userIdx));
//    }
//
//    @Auth
//    @DeleteMapping("/logout")
//    public ResponseCustom<Boolean> logout(
//            @ApiIgnore @IsLogin Long userIdx
//    ){
//        return ResponseCustom.OK(userService.logout(userIdx));
//    }
//
//
//    @ResponseBody
//    @GetMapping("/profile")
//    public ResponseCustom<?> getProfile(
//            @ApiIgnore @IsLogin Long userIdx
//    ){
//        return ResponseCustom.OK(userService.getProfile(userIdx));
//    }


    @Auth
    @GetMapping("/{nickName}")
    public ResponseCustom<?> searchUsers(
            @PathVariable("nickName") String nickName,
               @IsLogin LoginStatus loginStatus
    ) {
        return ResponseCustom.OK(userService.searchUsers(nickName));
    }

//    @Auth
//    @GetMapping("/fridge/{members}")
//    public ResponseCustom<?> searchFridgeUsers(
//            @PathVariable("members") String nickName,
//            @IsLogin LoginStatus loginStatus
//    ) {
//        return ResponseCustom.OK(userService.searchFridgeUsers(fridgeUserIdx));
//    }




}