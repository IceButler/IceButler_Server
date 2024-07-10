package com.example.icebutler_server.fridge.controller;

import com.example.icebutler_server.fridge.dto.request.*;
import com.example.icebutler_server.fridge.dto.response.*;
import com.example.icebutler_server.fridge.service.FridgeServiceImpl;
import com.example.icebutler_server.global.dto.response.ResponseCustom;
import com.example.icebutler_server.global.dto.response.SwaggerApiSuccess;
import com.example.icebutler_server.global.resolver.Auth;
import com.example.icebutler_server.global.resolver.IsLogin;
import com.example.icebutler_server.global.resolver.LoginStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/fridges")
@RestController
@RequiredArgsConstructor
@Tag(name = "Fridge", description = "냉장고 API")
public class FridgeController {

    private final FridgeServiceImpl fridgeService;

    @GetMapping("/health")
    public ResponseCustom<Void> healthCheck() {
        return ResponseCustom.success();
    }


    @Operation(summary = "냉장고 추가", description = "냉장고를 추가한다.")
    @SwaggerApiSuccess(implementation = ResponseCustom.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "냉장고 이름를 입력하지 않았습니다.\t\n" +
                    "존재하지 않는 냉장고 유형입니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
            @ApiResponse(responseCode = "404", description = "요청한 id를 가진 유저를 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
    })
    @Auth
    @PostMapping("/register")
    public ResponseCustom<Long> registerFridge(@RequestBody FridgeRegisterReq fridgeRegisterReq,
                                               @Parameter(hidden = true) @IsLogin LoginStatus loginStatus) {
        return ResponseCustom.success(fridgeService.registerFridge(fridgeRegisterReq, loginStatus.getUserId()));
    }

    @Operation(summary = "냉장고 수정", description = "냉장고를 수정한다.")
    @SwaggerApiSuccess(implementation = ResponseCustom.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "냉장고 이름를 입력하지 않았습니다.\t\n" +
                    "존재하지 않는 냉장고 유형입니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
            @ApiResponse(responseCode = "404", description = "요청한 id를 가진 유저를 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
    })
    @Auth
    @PatchMapping("/{fridgeId}")
    public ResponseCustom<?> modifyFridge(@Parameter(name = "냉장고 ID") @PathVariable Long fridgeId,
                                          @RequestBody FridgeModifyReq fridgeModifyReq,
                                          @Parameter(hidden = true) @IsLogin LoginStatus loginStatus) {
        fridgeService.modifyFridge(fridgeId, fridgeModifyReq, loginStatus.getUserId());
        return ResponseCustom.success();
    }

    @Operation(summary = "냉장고 삭제", description = "냉장고를 삭제한다.")
    @SwaggerApiSuccess(implementation = ResponseCustom.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "403", description = "냉장고의 멤버가 아닙니다.\t\n" +
                    "올바르지 않은 접근 권한입니다.\t\n" +
                    "올바르지 않은 냉장고 삭제 조건입니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
            @ApiResponse(responseCode = "404", description = "요청한 id를 가진 유저를 찾을 수 없습니다.\t\n" +
                    "요청한 id를 가진 냉장고를 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
    })
    @Auth
    @PatchMapping("/{fridgeId}/remove")
    public ResponseCustom<Long> removeFridge(@Parameter(name = "냉장고 ID") @PathVariable Long fridgeId,
                                             @Parameter(hidden = true) @IsLogin LoginStatus loginStatus) {
        return ResponseCustom.success(fridgeService.removeFridge(fridgeId, loginStatus.getUserId()));
    }

    @Operation(summary = "냉장고 사용자 삭제", description = "냉장고 사용자를 삭제한다.")
    @SwaggerApiSuccess(implementation = ResponseCustom.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "403", description = "냉장고의 멤버가 아닙니다.\t\n" +
                    "올바르지 않은 접근 권한입니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
            @ApiResponse(responseCode = "404", description = "요청한 id를 가진 유저를 찾을 수 없습니다.\t\n" +
                    "요청한 id를 가진 냉장고를 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
    })
    @Auth
    @PatchMapping("/{fridgeId}/remove/each")
    public ResponseCustom<Long> removeFridgeUser(@Parameter(name = "냉장고 ID") @PathVariable Long fridgeId,
                                                 @Parameter(hidden = true) @IsLogin LoginStatus loginStatus) {
        return ResponseCustom.success(fridgeService.removeFridgeUser(fridgeId, loginStatus.getUserId()));
    }

    @Operation(summary = "냉장고 식품 전체 조회(카테고리별)", description = "냉장고 내 식품을 전체조회한다.")
    @SwaggerApiSuccess(implementation = FridgeMainRes.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "존재하지 않는 카테고리입니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
            @ApiResponse(responseCode = "404", description = "요청한 id를 가진 유저를 찾을 수 없습니다.\t\n" +
                    "요청한 id를 가진 냉장고를 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
    })
    @Auth
    @GetMapping("/{fridgeId}/foods")
    public ResponseCustom<FridgeMainRes> getFoods(@Parameter(name = "냉장고 ID") @PathVariable Long fridgeId,
                                                  @Parameter(hidden = true) @IsLogin LoginStatus loginStatus,
                                                  @Parameter(name = "식품 카테고리") @RequestParam(required = false) String category) {
        return ResponseCustom.success(fridgeService.getFoods(fridgeId, loginStatus.getUserId(), category));
    }


    @Operation(summary = "냉장고 식품 검색 조회", description = "냉장고 내 식품을 검색한다.")
    @SwaggerApiSuccess(implementation = FridgeFoodsRes.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "요청한 id를 가진 냉장고를 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
    })
    @Auth
    @GetMapping("/{fridgeId}/search")
    public ResponseCustom<List<FridgeFoodsRes>> searchFridgeFood(@Parameter(name = "냉장고 ID") @PathVariable Long fridgeId,
                                                                 @Parameter(name = "식품명") @RequestParam String keyword,
                                                                 @Parameter(hidden = true) @IsLogin LoginStatus loginStatus) {
        return ResponseCustom.success(fridgeService.searchFridgeFood(fridgeId, loginStatus.getUserId(), keyword));
    }

    @Operation(summary = "냉장고 식품 상세 조회", description = "냉장고 내 식품을 상세 조회한다.")
    @SwaggerApiSuccess(implementation = FridgeFoodsRes.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "403", description = "냉장고의 멤버가 아닙니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
            @ApiResponse(responseCode = "404", description = "요청한 id를 가진 유저를 찾을 수 없습니다.\t\n" +
                    "요청한 id를 가진 냉장고를 찾을 수 없습니다.\t\n" +
                    "요청한 id를 가진 냉장고 내 식품을 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
    })
    @Auth
    @GetMapping("/{fridgeId}/foods/{fridgeFoodId}")
    public ResponseCustom<FridgeFoodRes> getFridgeFood(@Parameter(name = "냉장고 ID") @PathVariable Long fridgeId,
                                                       @Parameter(name = "냉장고 내 식품 ID") @PathVariable Long fridgeFoodId,
                                                       @Parameter(hidden = true) @IsLogin LoginStatus loginStatus) {
        return ResponseCustom.success(fridgeService.getFridgeFood(fridgeId, fridgeFoodId, loginStatus.getUserId()));
    }

    @Operation(summary = "냉장고 식품 추가", description = "냉장고 내 식품을 추가한다.")
    @SwaggerApiSuccess(implementation = ResponseCustom.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "존재하지 않는 카테고리입니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
            @ApiResponse(responseCode = "403", description = "냉장고의 멤버가 아닙니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
            @ApiResponse(responseCode = "404", description = "요청한 id를 가진 유저를 찾을 수 없습니다.\t\n" +
                    "요청한 id를 가진 냉장고를 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
    })
    @Auth
    @PostMapping("/{fridgeId}/food")
    public ResponseCustom<?> addFridgeFood(@RequestBody FridgeFoodsReq fridgeFoodsReq,
                                           @Parameter(name = "냉장고 ID") @PathVariable Long fridgeId,
                                           @Parameter(hidden = true) @IsLogin LoginStatus loginStatus) {
        fridgeService.addFridgeFood(fridgeFoodsReq, fridgeId, loginStatus.getUserId());
        return ResponseCustom.success();
    }

    @Operation(summary = "냉장고 식품 수정", description = "냉장고 내 식품을 수정한다.")
    @SwaggerApiSuccess(implementation = ResponseCustom.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "존재하지 않는 카테고리입니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
            @ApiResponse(responseCode = "403", description = "냉장고의 멤버가 아닙니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
            @ApiResponse(responseCode = "404", description = "요청한 id를 가진 유저를 찾을 수 없습니다.\t\n" +
                    "요청한 id를 가진 냉장고를 찾을 수 없습니다.\t\n" +
                    "요청한 id를 가진 냉장고 내 식품을 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
    })
    @Auth
    @PatchMapping("/{fridgeId}/foods/{fridgeFoodId}")
    public ResponseCustom<?> modifyFridgeFood(@RequestBody FridgeFoodReq fridgeFoodReq,
                                              @Parameter(name = "냉장고 ID") @PathVariable Long fridgeId,
                                              @Parameter(name = "냉장고 내 식품 ID") @PathVariable Long fridgeFoodId,
                                              @Parameter(hidden = true) @IsLogin LoginStatus loginStatus) {
        fridgeService.modifyFridgeFood(fridgeId, fridgeFoodId, fridgeFoodReq, loginStatus.getUserId());
        return ResponseCustom.success();
    }

    @Operation(summary = "냉장고 식품 삭제", description = "냉장고 내 식품을 삭제한다.")
    @SwaggerApiSuccess(implementation = ResponseCustom.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "존재하지 않는 식품삭제 타입입니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
            @ApiResponse(responseCode = "403", description = "냉장고의 멤버가 아닙니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
            @ApiResponse(responseCode = "404", description = "요청한 id를 가진 유저를 찾을 수 없습니다.\t\n" +
                    "요청한 id를 가진 냉장고를 찾을 수 없습니다.\t\n" +
                    "요청한 id를 가진 냉장고 내 식품을 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
    })
    @Auth
    @DeleteMapping("/{fridgeId}/foods")
    public ResponseCustom<?> deleteFridgeFood(@RequestBody DeleteFridgeFoodsReq deleteFridgeFoodsReq,
                                              @Parameter(name = "삭제 타입(폐기/섭취)") @RequestParam String type,
                                              @Parameter(name = "냉장고 ID") @PathVariable Long fridgeId,
                                              @Parameter(hidden = true) @IsLogin LoginStatus loginStatus) {
        fridgeService.deleteFridgeFood(deleteFridgeFoodsReq, type, fridgeId, loginStatus.getUserId());
        return ResponseCustom.success();
    }

    @Operation(summary = "냉장고 멤버 조회", description = "냉장고의 멤버를 조회한다.")
    @SwaggerApiSuccess(implementation = FridgeUserMainRes.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "요청한 id를 가진 냉장고를 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
    })
    @Auth
    @GetMapping("{fridgeId}/members")
    public ResponseCustom<FridgeUserMainRes> getMembers(
            @Parameter(name = "냉장고 ID") @PathVariable Long fridgeId,
            @Parameter(hidden = true) @IsLogin LoginStatus loginStatus
    ) {
        return ResponseCustom.success(fridgeService.searchMembers(fridgeId, loginStatus.getUserId()));
    }

    @Operation(summary = "냉장고 선택목록 조회", description = "냉장고 선택목록을 조회한다.")
    @SwaggerApiSuccess(implementation = SelectFridgesMainRes.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "요청한 id를 가진 유저를 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
    })
    @Auth
    @GetMapping("/select")
    public ResponseCustom<SelectFridgesMainRes> selectFridges(
            @Parameter(hidden = true) @IsLogin LoginStatus loginStatus
    ) {
        return ResponseCustom.success(fridgeService.selectFridges(loginStatus.getUserId()));
    }

    @Operation(summary = "냉장고 목록 조회", description = "냉장고 목록을 조회한다.")
    @SwaggerApiSuccess(implementation = GetFridgesMainRes.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "요청한 id를 가진 유저를 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
    })
    @Auth
    @GetMapping("")
    public ResponseCustom<GetFridgesMainRes> myFridge(@Parameter(hidden = true) @IsLogin LoginStatus loginStatus) {
        return ResponseCustom.success(fridgeService.myFridge(loginStatus.getUserId()));
    }

    /**
     * [Get] 냉장고 통계 (낭비/소비)
     */
    @Auth
    @GetMapping("/{fridgeId}/statistics")
    public ResponseCustom<?> getFridgeFoodStatistics(@Parameter(name = "냉장고 ID") @PathVariable Long fridgeId,
                                                     @Parameter(name = "통계 타입(낭비/소비)") @RequestParam String deleteCategory,
                                                     @Parameter(name = "연도") @RequestParam Integer year,
                                                     @Parameter(name = "월") @RequestParam Integer month,
                                                     @Parameter(hidden = true) @IsLogin LoginStatus status) {
        return ResponseCustom.success(fridgeService.getFridgeFoodStatistics(fridgeId, deleteCategory, status.getUserId(), year, month));
    }


    // 알림
    @Scheduled(cron = "0 50 18 * * ?", zone = "GMT+9:00")
    public void notifyFridgeFood() {
        fridgeService.notifyFridgeFood();
    }

    // 레시피 정보 전달 api

    /**
     * [Get] 사용자가 속한 냉장고 food list
     */
    // todo: 토큰 추가하기
    @GetMapping("/food-lists")
    public ResponseCustom<?> getFridgeUserFoodList(@RequestParam(required = false) Long fridgeId,
                                                   @RequestParam Long userId) {
        return ResponseCustom.success(this.fridgeService.getFridgeUserFoodList(fridgeId, userId));
    }
}
