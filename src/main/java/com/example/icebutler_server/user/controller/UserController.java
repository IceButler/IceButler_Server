package com.example.icebutler_server.user.controller;

import com.example.icebutler_server.global.dto.response.ResponseCustom;
import com.example.icebutler_server.global.resolver.IsLogin;
import com.example.icebutler_server.global.resolver.LoginStatus;
import com.example.icebutler_server.user.dto.request.PatchProfileReq;
import com.example.icebutler_server.user.dto.request.PostNicknameReq;
import com.example.icebutler_server.user.dto.request.PostUserReq;
import com.example.icebutler_server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.example.icebutler_server.global.resolver.Auth;

@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @ResponseBody
  @PostMapping("/login")
  public ResponseCustom<?> login(@RequestBody PostUserReq postUserReq) {
    return ResponseCustom.OK(userService.signUpOrLogin(postUserReq));
  }

  @ResponseBody
  @PatchMapping("/profile")
  public ResponseCustom<?> modifyProfile(@RequestBody PatchProfileReq patchProfileReq,
                                         @IsLogin LoginStatus loginStatus) {
    userService.modifyProfile(loginStatus.getUserIdx(), patchProfileReq);
    return ResponseCustom.OK();
  }

  @ResponseBody
  @PostMapping("/nickname")
  public ResponseCustom<?> checkNickname(@RequestBody PostNicknameReq postNicknameReq) {
    userService.checkNickname(postNicknameReq);
    return ResponseCustom.OK();
  }

  //유저 탈퇴
  @Auth
  @DeleteMapping("/delete")
  public ResponseCustom<?> deleteUser(
          @IsLogin LoginStatus loginStatus
  ) {
    userService.deleteUser(loginStatus.getUserIdx());
    return ResponseCustom.OK();
  }

  //유저 로그아웃
  @Auth
  @PostMapping("/logout")
  public ResponseCustom<?> logout(
          @IsLogin LoginStatus loginStatus
  ) {
    userService.logout(loginStatus.getUserIdx());
    return ResponseCustom.OK();
  }

  // 마이페이지 조회
  @Auth
  @GetMapping("")
  public ResponseCustom<?> profile(
          @IsLogin LoginStatus loginStatus
  ) {
    return ResponseCustom.OK(userService.myProfile(loginStatus.getUserIdx()));
  }

}
