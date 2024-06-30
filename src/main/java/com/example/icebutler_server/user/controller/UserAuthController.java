package com.example.icebutler_server.user.controller;

import com.example.icebutler_server.global.dto.response.ResponseCustom;
import com.example.icebutler_server.global.resolver.Auth;
import com.example.icebutler_server.global.resolver.IsLogin;
import com.example.icebutler_server.global.resolver.LoginStatus;
import com.example.icebutler_server.global.util.TokenUtils;
import com.example.icebutler_server.user.dto.request.PatchProfileReq;
import com.example.icebutler_server.user.dto.response.MyProfileRes;
import com.example.icebutler_server.user.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RequestMapping(value = "/users")
@RestController
@Tag(name = "User", description = "유저 API (인증 필요)")
 @SecurityRequirement(name = "Bearer")
public class UserAuthController {

  private final TokenUtils tokenUtils;
  private final UserService userService;

  @Auth
  @GetMapping("/renew")
  public ResponseCustom<String> accessToken(@IsLogin LoginStatus loginStatus) {
    return ResponseCustom.OK(tokenUtils.accessExpiration(loginStatus.getUserIdx()));
  }

  @Auth
  @ResponseBody
  @PatchMapping("/profile")
  public ResponseCustom<?> modifyProfile(@RequestBody PatchProfileReq patchProfileReq,
                                         @Parameter(hidden = true) @IsLogin LoginStatus loginStatus) {
    userService.modifyProfile(loginStatus.getUserIdx(), patchProfileReq);
    return ResponseCustom.OK();
  }

  @Auth
  @DeleteMapping("/delete")
  public ResponseCustom<?> deleteUser(
          @IsLogin LoginStatus loginStatus
  ) {
    userService.deleteUser(loginStatus.getUserIdx());
    return ResponseCustom.OK();
  }

  @Auth
  @PostMapping("/logout")
  public ResponseCustom<?> logout(
          @IsLogin LoginStatus loginStatus
  ) {
    userService.logout(loginStatus.getUserIdx());
    return ResponseCustom.OK();
  }

  @Auth
  @GetMapping("")
  public ResponseCustom<MyProfileRes> profile(
          @IsLogin LoginStatus loginStatus
  ) {
    return ResponseCustom.OK(userService.checkProfile(loginStatus.getUserIdx()));
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
