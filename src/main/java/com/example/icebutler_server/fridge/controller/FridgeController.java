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
        return ResponseCustom.success(fridgeService.registerFridge(fridgeRegisterReq, loginStatus.getUserIdx()));
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
    @PatchMapping("/{fridgeIdx}")
    public ResponseCustom<?> modifyFridge(@Parameter(name = "냉장고 ID") @PathVariable Long fridgeIdx,
                                          @RequestBody FridgeModifyReq fridgeModifyReq,
                                          @Parameter(hidden = true) @IsLogin LoginStatus loginStatus) {
        fridgeService.modifyFridge(fridgeIdx, fridgeModifyReq, loginStatus.getUserIdx());
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
    @PatchMapping("/{fridgeIdx}/remove")
    public ResponseCustom<Long> removeFridge(@Parameter(name = "냉장고 ID") @PathVariable Long fridgeIdx,
                                             @Parameter(hidden = true) @IsLogin LoginStatus loginStatus) {
        return ResponseCustom.success(fridgeService.removeFridge(fridgeIdx, loginStatus.getUserIdx()));
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
    @PatchMapping("/{fridgeIdx}/remove/each")
    public ResponseCustom<Long> removeFridgeUser(@Parameter(name = "냉장고 ID") @PathVariable Long fridgeIdx,
                                                 @Parameter(hidden = true) @IsLogin LoginStatus loginStatus) {
        return ResponseCustom.success(fridgeService.removeFridgeUser(fridgeIdx, loginStatus.getUserIdx()));
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
    @GetMapping("/{fridgeIdx}/foods")
    public ResponseCustom<FridgeMainRes> getFoods(@Parameter(name = "냉장고 ID") @PathVariable Long fridgeIdx,
                                                  @Parameter(hidden = true) @IsLogin LoginStatus loginStatus,
                                                  @Parameter(name = "식품 카테고리") @RequestParam(required = false) String category) {
        return ResponseCustom.success(fridgeService.getFoods(fridgeIdx, loginStatus.getUserIdx(), category));
    }


    @Operation(summary = "냉장고 식품 검색 조회", description = "냉장고 내 식품을 검색한다.")
    @SwaggerApiSuccess(implementation = FridgeFoodsRes.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "요청한 id를 가진 냉장고를 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
    })
    @Auth
    @GetMapping("/{fridgeIdx}/search")
    public ResponseCustom<List<FridgeFoodsRes>> searchFridgeFood(@Parameter(name = "냉장고 ID") @PathVariable Long fridgeIdx,
                                                                 @Parameter(name = "식품명") @RequestParam String keyword,
                                                                 @Parameter(hidden = true) @IsLogin LoginStatus loginStatus) {
        return ResponseCustom.success(fridgeService.searchFridgeFood(fridgeIdx, loginStatus.getUserIdx(), keyword));
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
    @GetMapping("/{fridgeIdx}/foods/{fridgeFoodIdx}")
    public ResponseCustom<FridgeFoodRes> getFridgeFood(@Parameter(name = "냉장고 ID") @PathVariable Long fridgeIdx,
                                                       @Parameter(name = "냉장고 내 식품 ID") @PathVariable Long fridgeFoodIdx,
                                                       @Parameter(hidden = true) @IsLogin LoginStatus loginStatus) {
        return ResponseCustom.success(fridgeService.getFridgeFood(fridgeIdx, fridgeFoodIdx, loginStatus.getUserIdx()));
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
    @PostMapping("/{fridgeIdx}/food")
    public ResponseCustom<?> addFridgeFood(@RequestBody FridgeFoodsReq fridgeFoodsReq,
                                           @Parameter(name = "냉장고 ID") @PathVariable Long fridgeIdx,
                                           @Parameter(hidden = true) @IsLogin LoginStatus loginStatus) {
        fridgeService.addFridgeFood(fridgeFoodsReq, fridgeIdx, loginStatus.getUserIdx());
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
    @PatchMapping("/{fridgeIdx}/foods/{fridgeFoodIdx}")
    public ResponseCustom<?> modifyFridgeFood(@RequestBody FridgeFoodReq fridgeFoodReq,
                                              @Parameter(name = "냉장고 ID") @PathVariable Long fridgeIdx,
                                              @Parameter(name = "냉장고 내 식품 ID") @PathVariable Long fridgeFoodIdx,
                                              @Parameter(hidden = true) @IsLogin LoginStatus loginStatus) {
        fridgeService.modifyFridgeFood(fridgeIdx, fridgeFoodIdx, fridgeFoodReq, loginStatus.getUserIdx());
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
    @DeleteMapping("/{fridgeIdx}/foods")
    public ResponseCustom<?> deleteFridgeFood(@RequestBody DeleteFridgeFoodsReq deleteFridgeFoodsReq,
                                              @Parameter(name = "삭제 타입(폐기/섭취)") @RequestParam String type,
                                              @Parameter(name = "냉장고 ID") @PathVariable Long fridgeIdx,
                                              @Parameter(hidden = true) @IsLogin LoginStatus loginStatus) {
        fridgeService.deleteFridgeFood(deleteFridgeFoodsReq, type, fridgeIdx, loginStatus.getUserIdx());
        return ResponseCustom.success();
    }

    @Operation(summary = "냉장고 멤버 조회", description = "냉장고의 멤버를 조회한다.")
    @SwaggerApiSuccess(implementation = FridgeUserMainRes.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "요청한 id를 가진 냉장고를 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
    })
    @Auth
    @GetMapping("{fridgeIdx}/members")
    public ResponseCustom<FridgeUserMainRes> getMembers(
            @Parameter(name = "냉장고 ID") @PathVariable Long fridgeIdx,
            @Parameter(hidden = true) @IsLogin LoginStatus loginStatus
    ) {
        return ResponseCustom.success(fridgeService.searchMembers(fridgeIdx, loginStatus.getUserIdx()));
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
        return ResponseCustom.success(fridgeService.selectFridges(loginStatus.getUserIdx()));
    }

    @Operation(summary = "냉장고 목록 조회", description = "냉장고 목록을 조회한다.")
    @SwaggerApiSuccess(implementation = GetFridgesMainRes.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "요청한 id를 가진 유저를 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
    })
    @Auth
    @GetMapping("")
    public ResponseCustom<GetFridgesMainRes> myFridge(
            @Parameter(hidden = true) @IsLogin LoginStatus loginStatus
    ) {
        return ResponseCustom.success(fridgeService.myFridge(loginStatus.getUserIdx()));
    }

    /**
     * [Get] 냉장고 통계 (낭비/소비)
     */
    @Auth
    @GetMapping("/{fridgeIdx}/statistics")
    public ResponseCustom<?> getFridgeFoodStatistics(@Parameter(name = "냉장고 ID") @PathVariable Long fridgeIdx,
                                                     @Parameter(name = "통계 타입(낭비/소비)") @RequestParam String deleteCategory,
                                                     @Parameter(name = "연도") @RequestParam Integer year,
                                                     @Parameter(name = "월") @RequestParam Integer month,
                                                     @Parameter(hidden = true) @IsLogin LoginStatus status) {
        return ResponseCustom.success(fridgeService.getFridgeFoodStatistics(fridgeIdx, deleteCategory, status.getUserIdx(), year, month));
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
    public ResponseCustom<?> getFridgeUserFoodList(@RequestParam(name = "fridgeIdx", required = false) Long fridgeIdx,
                                                   @RequestParam(name = "userIdx") Long userIdx) {
        return ResponseCustom.success(this.fridgeService.getFridgeUserFoodList(fridgeIdx, userIdx));
    }
}
