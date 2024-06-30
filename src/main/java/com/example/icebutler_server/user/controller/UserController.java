package com.example.icebutler_server.user.controller;

import com.example.icebutler_server.global.dto.response.ResponseCustom;
import com.example.icebutler_server.global.dto.response.SwaggerApiSuccess;
import com.example.icebutler_server.user.dto.LoginUserReq;
import com.example.icebutler_server.user.dto.request.PostNicknameReq;
import com.example.icebutler_server.user.dto.request.PostUserReq;
import com.example.icebutler_server.user.dto.response.PostUserRes;
import com.example.icebutler_server.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "유저 API (인증 불필요)")
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

  @ResponseBody
  @PostMapping("/nickname")
  public ResponseCustom<?> checkNickname(@RequestBody PostNicknameReq postNicknameReq) {
    return ResponseCustom.OK(userService.checkNickname(postNicknameReq));
  }

  @GetMapping("/search")
  public ResponseCustom<?> searchNickname(
          @Parameter(name = "nickname", description = "닉네임") @RequestParam String nickname
  ) {
    return ResponseCustom.OK(userService.searchNickname(nickname));
  }

}
