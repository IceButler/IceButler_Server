//package com.example.icebutler_server.user.controller;
//
//import com.example.icebutler_server.global.dto.response.ResponseCustom;
//import com.example.icebutler_server.global.util.TokenUtils;
//import com.example.icebutler_server.user.dto.UserAuthTokenRequest;
//import io.swagger.annotations.Api;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//
//@RequiredArgsConstructor
//@RequestMapping("/api/v1/auth")
//@RestController
//@Api(tags = "User Auth 관련 API")
//public class UserAuthController {
//
//    private final TokenUtils tokenUtils;
//
//    @PostMapping("/renew")
//    public ResponseCustom<String> accessToken(@RequestBody
////                                                  @Valid
//                                                  UserAuthTokenRequest userAuthTokenRequest){
//        return ResponseCustom.OK(tokenUtils.accessExpiration(userAuthTokenRequest));
//    }
//
//
//}