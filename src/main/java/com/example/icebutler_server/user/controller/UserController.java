package com.example.icebutler_server.user.controller;

import com.example.icebutler_server.global.dto.response.ResponseCustom;
import com.example.icebutler_server.global.dto.response.SwaggerApiSuccess;
import com.example.icebutler_server.global.resolver.IsLogin;
import com.example.icebutler_server.global.resolver.LoginStatus;
import com.example.icebutler_server.user.dto.LoginUserReq;
import com.example.icebutler_server.user.dto.request.PatchProfileReq;
import com.example.icebutler_server.user.dto.request.PostNicknameReq;
import com.example.icebutler_server.user.dto.request.PostUserReq;
import com.example.icebutler_server.user.dto.response.MyNotificationRes;
import com.example.icebutler_server.user.dto.response.MyProfileRes;
import com.example.icebutler_server.user.dto.response.PostUserRes;
import com.example.icebutler_server.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import com.example.icebutler_server.global.resolver.Auth;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "유저 API")
// @SecurityRequirement(name = "Bearer")
public class UserController {

  private final UserService userService;

  @Operation(summary = "유저 회원가입", description = "유저가 회원가입한다.")
  @SwaggerApiSuccess(implementation = PostUserRes.class)
  @ApiResponses(value = {
          @ApiResponse(responseCode = "400", description = "부적절한 소셜로그인 provider 입력입니다.\t\n 사용자 이메일 값을 찾아올 수 없습니다.", content = @Content(schema = @Schema(implementation = ResponseCustom.class))),})
  @ResponseBody
  @PostMapping("/join")
  public ResponseCustom<PostUserRes> join(@RequestBody PostUserReq postUserReq) {
    return ResponseCustom.OK(userService.join(postUserReq));
  }

  @ResponseBody
  @PostMapping("/login")
  public ResponseCustom<PostUserRes> login(@RequestBody LoginUserReq loginUserReq) {
    return ResponseCustom.OK(userService.login(loginUserReq));
  }

  @Auth
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
    return ResponseCustom.OK(userService.checkNickname(postNicknameReq));
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
  public ResponseCustom<MyProfileRes> profile(
          @IsLogin LoginStatus loginStatus
  ) {
    System.out.println(loginStatus.getUserIdx());
    return ResponseCustom.OK(userService.checkProfile(loginStatus.getUserIdx()));
  }

  //유저 닉네임 검색 조회
  @GetMapping("/search")
  public ResponseCustom<?> searchNickname(
          @Parameter(name = "nickname", description = "닉네임") @RequestParam String nickname
  ) {
    return ResponseCustom.OK(userService.searchNickname(nickname));
  }

  @Auth
  @GetMapping("/notification")
  public ResponseCustom<?> getUserNotification(
          @IsLogin LoginStatus loginStatus,
          Pageable pageable
  ) {
    return ResponseCustom.OK(userService.getUserNotification(loginStatus.getUserIdx(), pageable));
  }

}
