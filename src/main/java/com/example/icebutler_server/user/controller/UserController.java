package com.example.icebutler_server.user.controller;

import com.example.icebutler_server.global.dto.response.ResponseCustom;
import com.example.icebutler_server.global.dto.response.SwaggerApiSuccess;
import com.example.icebutler_server.user.dto.LoginUserReq;
import com.example.icebutler_server.user.dto.request.PostNicknameReq;
import com.example.icebutler_server.user.dto.request.PostUserReq;
import com.example.icebutler_server.user.dto.response.NickNameRes;
import com.example.icebutler_server.user.dto.response.PostNickNameRes;
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

import java.util.List;

@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "유저 API (인증 불필요)")
public class UserController {

  private final UserService userService;

  @Operation(summary = "유저 회원가입", description = "유저가 회원가입한다.")
  @SwaggerApiSuccess(implementation = PostUserRes.class)
  @ApiResponses(value = {
          @ApiResponse(responseCode = "400", description = "(U0000)부적절한 소셜로그인 provider 입력입니다.", content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
          @ApiResponse(responseCode = "401", description = "(U0001)관리자에 의해 서비스 이용이 제한되었습니다.", content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
          @ApiResponse(responseCode = "404", description = "(U0002)사용자 이메일 값을 찾아올 수 없습니다.", content = @Content(schema = @Schema(implementation = ResponseCustom.class)))
  })
  @ResponseBody
  @PostMapping("/join")
  public ResponseCustom<PostUserRes> join(@RequestBody PostUserReq postUserReq) {
    return ResponseCustom.success(userService.join(postUserReq));
  }

  @Operation(summary = "유저 로그인", description = "유저가 로그인한다.")
  @SwaggerApiSuccess(implementation = PostUserRes.class)
  @ApiResponses(value = {
          @ApiResponse(responseCode = "400", description = "(U0000)부적절한 소셜로그인 provider 입력입니다.", content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
          @ApiResponse(responseCode = "404", description = "(U0002)사용자 이메일 값을 찾아올 수 없습니다." +
                                                          "(U0003)이미 탈퇴한 회원입니다.", content = @Content(schema = @Schema(implementation = ResponseCustom.class)))
  })
  @ResponseBody
  @PostMapping("/login")
  public ResponseCustom<PostUserRes> login(@RequestBody LoginUserReq loginUserReq) {
    return ResponseCustom.success(userService.login(loginUserReq));
  }

  @Operation(summary = "유저 닉네임 중복 조회", description = "닉네임 중복 여부를 조회한다.")
  @SwaggerApiSuccess(implementation = PostNickNameRes.class)
  @ApiResponses(value = {
          @ApiResponse(responseCode = "400", description = "(U0004)올바르지 않은 닉네임 형식입니다.", content = @Content(schema = @Schema(implementation = ResponseCustom.class)))
  })
  @ResponseBody
  @PostMapping("/nickname")
  public ResponseCustom<PostNickNameRes> checkNickname(@RequestBody PostNicknameReq postNicknameReq) {
    return ResponseCustom.success(userService.checkNickname(postNicknameReq));
  }

  @Operation(summary = "유저 닉네임 검색 조회", description = "닉네임으로 유저를 검색한다.")
  @SwaggerApiSuccess(implementation = NickNameRes.class)
  @GetMapping("/search")
  public ResponseCustom<List<NickNameRes>> searchNickname(
          @Parameter(name = "nickname", description = "닉네임") @RequestParam String nickname) {
    return ResponseCustom.success(userService.searchNickname(nickname));
  }

}
