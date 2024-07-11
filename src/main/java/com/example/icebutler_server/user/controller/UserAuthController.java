package com.example.icebutler_server.user.controller;

import com.example.icebutler_server.global.dto.response.ResponseCustom;
import com.example.icebutler_server.global.dto.response.SwaggerApiSuccess;
import com.example.icebutler_server.global.resolver.Auth;
import com.example.icebutler_server.global.resolver.IsLogin;
import com.example.icebutler_server.global.resolver.LoginStatus;
import com.example.icebutler_server.global.util.TokenUtils;
import com.example.icebutler_server.user.dto.request.PatchProfileReq;
import com.example.icebutler_server.user.dto.response.MyNotificationRes;
import com.example.icebutler_server.user.dto.response.MyProfileRes;
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
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping(value = "/users")
@RestController
@Tag(name = "UserAuth", description = "유저 API (인증 필요)")
@SecurityRequirement(name = "Bearer")
public class UserAuthController {

    private final TokenUtils tokenUtils;
    private final UserService userService;

    @Operation(summary = "토큰 재발급", description = "만료된 토큰을 재발급한다.")
    @SwaggerApiSuccess(implementation = String.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "(A0000)만료된 토큰입니다. 다시 발급해주세요.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class)))
    })
    @Auth
    @GetMapping("/renew")
    public ResponseCustom<String> accessToken(@Parameter(hidden = true) @IsLogin LoginStatus loginStatus) {
        return ResponseCustom.success(tokenUtils.accessExpiration(loginStatus.getUserId()));
    }

    @Operation(summary = "유저 프로필 수정", description = "유저 프로필을 수정한다.")
    @SwaggerApiSuccess(implementation = ResponseCustom.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "(U0000)존재하지 않는 사용자입니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class)))
    })
    @Auth
    @ResponseBody
    @PatchMapping("/profile")
    public ResponseCustom<?> modifyProfile(@RequestBody PatchProfileReq patchProfileReq,
                                           @Parameter(hidden = true) @IsLogin LoginStatus loginStatus) {
        userService.modifyProfile(loginStatus.getUserId(), patchProfileReq);
        return ResponseCustom.success();
    }

    @Operation(summary = "유저 탈퇴", description = "유저를 탈퇴한다.")
    @SwaggerApiSuccess(implementation = ResponseCustom.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "(U0000)존재하지 않는 사용자입니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
            @ApiResponse(responseCode = "409", description = "(R0001)해당 냉장고에 사용자가 존재합니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
    })
    @Auth
    @DeleteMapping("/delete")
    public ResponseCustom<?> deleteUser(
            @Parameter(hidden = true) @IsLogin LoginStatus loginStatus) {
        userService.deleteUser(loginStatus.getUserId());
        return ResponseCustom.success();
    }

    @Operation(summary = "유저 로그아웃", description = "유저를 로그아웃한다.")
    @SwaggerApiSuccess(implementation = ResponseCustom.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "(U0000)존재하지 않는 사용자입니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
    })
    @Auth
    @PostMapping("/logout")
    public ResponseCustom<?> logout(
            @Parameter(hidden = true) @IsLogin LoginStatus loginStatus) {
        userService.logout(loginStatus.getUserId());
        return ResponseCustom.success();
    }

    @Operation(summary = "유저 프로필 조회", description = "유저 프로필을 조회한다.")
    @SwaggerApiSuccess(implementation = MyProfileRes.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "(U0000)존재하지 않는 사용자입니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class)))
    })
    @Auth
    @GetMapping("")
    public ResponseCustom<MyProfileRes> profile(
            @Parameter(hidden = true) @IsLogin LoginStatus loginStatus) {
        return ResponseCustom.success(userService.checkProfile(loginStatus.getUserId()));
    }

    @Operation(summary = "유저 알림 목록", description = "유저 알림 목록을 조회한다.")
    @SwaggerApiSuccess(implementation = MyNotificationRes.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "(U0000)존재하지 않는 사용자입니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
    })
    @Auth
    @GetMapping("/notification")
    public ResponseCustom<Page<MyNotificationRes>> getUserNotification(
            @Parameter(hidden = true) @IsLogin LoginStatus loginStatus,
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseCustom.success(userService.getUserNotification(loginStatus.getUserId(), pageable));
    }

}
