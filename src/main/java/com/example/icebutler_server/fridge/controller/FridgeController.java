package com.example.icebutler_server.fridge.controller;

import com.example.icebutler_server.fridge.dto.request.*;
import com.example.icebutler_server.fridge.dto.response.*;
import com.example.icebutler_server.fridge.service.FridgeServiceImpl;
import com.example.icebutler_server.global.dto.response.ResponseCustom;
import com.example.icebutler_server.global.dto.response.SwaggerApiSuccess;
import com.example.icebutler_server.global.resolver.Auth;
import com.example.icebutler_server.global.resolver.IsLogin;
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

import javax.validation.Valid;
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
            @ApiResponse(responseCode = "400", description = "(G0000)잘못된 파라미터입니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
            @ApiResponse(responseCode = "404", description = "(U0000)존재하지 않는 사용자입니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
    })
    @Auth
    @PostMapping
    public ResponseCustom<Long> addFridge(@Valid @RequestBody AddFridgeReq addFridgeReq,
                                          @Parameter(hidden = true) @IsLogin Long userId) {
        return ResponseCustom.success(fridgeService.addFridge(addFridgeReq, userId));
    }

    @Operation(summary = "냉장고 정보 수정", description = "주인이 냉장고 정보를 수정한다.")
    @SwaggerApiSuccess(implementation = ResponseCustom.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "(G0000)잘못된 파라미터입니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
            @ApiResponse(responseCode = "403", description = "(G0001)권한이 없습니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
            @ApiResponse(responseCode = "404", description = "(U0000)존재하지 않는 사용자입니다.\t\n" +
                    "(R0000)존재하지 않는 냉장고입니다.\t\n" +
                    "(R0003)해당 냉장고에 존재하지 않는 사용자입니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
    })
    @Auth
    @PatchMapping("/{fridgeId}")
    public ResponseCustom<Void> modifyFridge(@Parameter(description = "냉장고 ID") @PathVariable Long fridgeId,
                                          @Valid @RequestBody EditFridgeReq editFridgeReq,
                                          @Parameter(hidden = true) @IsLogin Long userId) {
        fridgeService.modifyFridge(fridgeId, editFridgeReq, userId);
        return ResponseCustom.success();
    }

    @Operation(summary = "냉장고 삭제", description = "주인이 냉장고를 삭제한다.")
    @SwaggerApiSuccess(implementation = ResponseCustom.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "403", description = "(G0001)권한이 없습니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
            @ApiResponse(responseCode = "404", description = "(R0000)존재하지 않는 냉장고입니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
            @ApiResponse(responseCode = "409", description = "(R0001)해당 냉장고에 사용자가 존재합니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
    })
    @Auth
    @DeleteMapping("/{fridgeId}")
    public ResponseCustom<Void> removeFridge(@Parameter(description = "냉장고 ID") @PathVariable Long fridgeId,
                                             @Parameter(hidden = true) @IsLogin Long userId) {
        fridgeService.removeFridge(fridgeId, userId);
        return ResponseCustom.success();
    }

    @Operation(summary = "냉장고 사용자 삭제", description = "냉장고 사용자를 삭제한다.")
    @SwaggerApiSuccess(implementation = ResponseCustom.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "403", description = "(G0001)권한이 없습니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
            @ApiResponse(responseCode = "404", description = "(U0000)존재하지 않는 사용자입니다.\t\n" +
                    "(R0000)존재하지 않는 냉장고입니다.\t\n" +
                    "(R0003)해당 냉장고에 존재하지 않는 사용자입니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
    })
    @Auth
    @PatchMapping("/{fridgeId}/remove/each")
    public ResponseCustom<Long> removeFridgeUser(@Parameter(description = "냉장고 ID") @PathVariable Long fridgeId,
                                                 @Parameter(hidden = true) @IsLogin Long userId) {
        return ResponseCustom.success(fridgeService.removeFridgeUser(fridgeId, userId));
    }

    @Operation(summary = "냉장고 식품 전체 조회(카테고리별)", description = "냉장고 내 식품을 카테고리 별로 전체조회한다.")
    @SwaggerApiSuccess(implementation = FridgeMainRes.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "(F0000)존재하지 않는 카테고리입니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
            @ApiResponse(responseCode = "404", description = "(U0000)존재하지 않는 사용자입니다.\t\n" +
                    "(R0000)존재하지 않는 냉장고입니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
    })
    @Auth
    @GetMapping("/{fridgeId}/foods")
    public ResponseCustom<FridgeMainRes> getFoods(@Parameter(description = "냉장고 ID") @PathVariable Long fridgeId,
                                                  @Parameter(description = "식품 카테고리") @RequestParam(required = false) String category,
                                                  @Parameter(hidden = true) @IsLogin Long userId) {
        return ResponseCustom.success(fridgeService.getFoods(fridgeId, userId, category));
    }


    @Operation(summary = "냉장고 식품 검색 조회", description = "냉장고 내 식품을 검색한다.")
    @SwaggerApiSuccess(implementation = FridgeFoodsRes.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "(R0000)존재하지 않는 냉장고입니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
    })
    @Auth
    @GetMapping("/{fridgeId}/search")
    public ResponseCustom<List<FridgeFoodsRes>> searchFridgeFood(@Parameter(description = "냉장고 ID") @PathVariable Long fridgeId,
                                                                 @Parameter(description = "식품명") @RequestParam String keyword,
                                                                 @Parameter(hidden = true) @IsLogin Long userId) {
        return ResponseCustom.success(fridgeService.searchFridgeFood(fridgeId, userId, keyword));
    }

    @Operation(summary = "냉장고 식품 상세 조회", description = "냉장고 내 식품을 상세 조회한다.")
    @SwaggerApiSuccess(implementation = FridgeFoodsRes.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "403", description = "(G0001)권한이 없습니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
            @ApiResponse(responseCode = "404", description = "(U0000)존재하지 않는 사용자입니다.\t\n" +
                    "(R0000)존재하지 않는 냉장고입니다.\t\n" +
                    "(R0002)해당 냉장고에 존재하지 않는 식품입니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
    })
    @Auth
    @GetMapping("/{fridgeId}/foods/{fridgeFoodId}")
    public ResponseCustom<FridgeFoodRes> getFridgeFood(@Parameter(description = "냉장고 ID") @PathVariable Long fridgeId,
                                                       @Parameter(description = "냉장고 내 식품 ID") @PathVariable Long fridgeFoodId,
                                                       @Parameter(hidden = true) @IsLogin Long userId) {
        return ResponseCustom.success(fridgeService.getFridgeFood(fridgeId, fridgeFoodId, userId));
    }

    @Operation(summary = "냉장고 식품 추가", description = "냉장고 내 식품을 추가한다.")
    @SwaggerApiSuccess(implementation = ResponseCustom.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "(F0000)존재하지 않는 카테고리입니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
            @ApiResponse(responseCode = "403", description = "(G0001)권한이 없습니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
            @ApiResponse(responseCode = "404", description = "(U0000)존재하지 않는 사용자입니다.\t\n" +
                    "(R0000)존재하지 않는 냉장고입니다.\t\n" +
                    "(R0003)해당 냉장고에 존재하지 않는 사용자입니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
    })
    @Auth
    @PostMapping("/{fridgeId}/food")
    public ResponseCustom<?> addFridgeFood(@RequestBody FridgeFoodsReq fridgeFoodsReq,
                                           @Parameter(description = "냉장고 ID") @PathVariable Long fridgeId,
                                           @Parameter(hidden = true) @IsLogin Long userId) {
        fridgeService.addFridgeFood(fridgeFoodsReq, fridgeId, userId);
        return ResponseCustom.success();
    }

    @Operation(summary = "냉장고 식품 수정", description = "냉장고 내 식품을 수정한다.")
    @SwaggerApiSuccess(implementation = ResponseCustom.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "(F0000)존재하지 않는 카테고리입니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
            @ApiResponse(responseCode = "403", description = "(G0001)권한이 없습니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
            @ApiResponse(responseCode = "404", description = "(U0000)존재하지 않는 사용자입니다.\t\n" +
                    "(R0000)존재하지 않는 냉장고입니다.\t\n" +
                    "(R0002)해당 냉장고에 존재하지 않는 식품입니다.\t\n" +
                    "(R0003)해당 냉장고에 존재하지 않는 사용자입니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
    })
    @Auth
    @PatchMapping("/{fridgeId}/foods/{fridgeFoodId}")
    public ResponseCustom<?> modifyFridgeFood(@RequestBody FridgeFoodReq fridgeFoodReq,
                                              @Parameter(description = "냉장고 ID") @PathVariable Long fridgeId,
                                              @Parameter(description = "냉장고 내 식품 ID") @PathVariable Long fridgeFoodId,
                                              @Parameter(hidden = true) @IsLogin Long userId) {
        fridgeService.modifyFridgeFood(fridgeId, fridgeFoodId, fridgeFoodReq, userId);
        return ResponseCustom.success();
    }

    @Operation(summary = "냉장고 식품 삭제", description = "냉장고 내 식품을 삭제한다.")
    @SwaggerApiSuccess(implementation = ResponseCustom.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "(F0003)존재하지 않는 식품삭제 타입입니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
            @ApiResponse(responseCode = "403", description = "(G0001)권한이 없습니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
            @ApiResponse(responseCode = "404", description = "(U0000)존재하지 않는 사용자입니다.\t\n" +
                    "(R0000)존재하지 않는 냉장고입니다.\t\n" +
                    "(R0002)해당 냉장고에 존재하지 않는 식품입니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
    })
    @Auth
    @DeleteMapping("/{fridgeId}/foods")
    public ResponseCustom<?> deleteFridgeFood(@RequestBody DeleteFridgeFoodsReq deleteFridgeFoodsReq,
                                              @Parameter(description = "삭제 타입(폐기/섭취)") @RequestParam String type,
                                              @Parameter(description = "냉장고 ID") @PathVariable Long fridgeId,
                                              @Parameter(hidden = true) @IsLogin Long userId) {
        fridgeService.deleteFridgeFood(deleteFridgeFoodsReq, type, fridgeId, userId);
        return ResponseCustom.success();
    }

    @Operation(summary = "냉장고 멤버 조회", description = "냉장고의 멤버를 조회한다.")
    @SwaggerApiSuccess(implementation = FridgeUserMainRes.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "(R0000)존재하지 않는 냉장고입니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
    })
    @Auth
    @GetMapping("{fridgeId}/members")
    public ResponseCustom<FridgeUserMainRes> getMembers(
            @Parameter(description = "냉장고 ID") @PathVariable Long fridgeId,
            @Parameter(hidden = true) @IsLogin Long userId) {
        return ResponseCustom.success(fridgeService.searchMembers(fridgeId, userId));
    }

    @Operation(summary = "냉장고 선택목록 조회", description = "냉장고 선택목록을 조회한다.")
    @SwaggerApiSuccess(implementation = SelectFridgesMainRes.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "(U0000)존재하지 않는 사용자입니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
    })
    @Auth
    @GetMapping("/select")
    public ResponseCustom<SelectFridgesMainRes> selectFridges(
            @Parameter(hidden = true) @IsLogin Long userId
    ) {
        return ResponseCustom.success(fridgeService.selectFridges(userId));
    }

    @Operation(summary = "냉장고 목록 조회", description = "냉장고 목록을 조회한다.")
    @SwaggerApiSuccess(implementation = GetFridgesMainRes.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "(U0000)존재하지 않는 사용자입니다.\t\n" +
                    "(R0000)존재하지 않는 냉장고입니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
    })
    @Auth
    @GetMapping("")
    public ResponseCustom<GetFridgesMainRes> myFridge(@Parameter(hidden = true) @IsLogin Long userId) {
        return ResponseCustom.success(fridgeService.myFridge(userId));
    }

    /**
     * [Get] 냉장고 통계 (낭비/소비)
     */
    @Operation(summary = "냉장고 식품 삭제 통계 조회", description = "냉장고 식품의 삭제 타입별 통계를 조회한다.")
    @SwaggerApiSuccess(implementation = GetFridgesMainRes.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "(F0003)존재하지 않는 식품삭제 타입입니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
            @ApiResponse(responseCode = "403", description = "(G0001)권한이 없습니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
            @ApiResponse(responseCode = "404", description = "(U0000)존재하지 않는 사용자입니다.\t\n" +
                    "(R0000)존재하지 않는 냉장고입니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
    })
    @Auth
    @GetMapping("/{fridgeId}/statistics")
    public ResponseCustom<FridgeFoodsStatistics> getFridgeFoodStatistics(
            @Parameter(description = "냉장고 ID") @PathVariable Long fridgeId,
            @Parameter(description = "통계 타입(낭비/소비)") @RequestParam String deleteCategory,
            @Parameter(description = "연도") @RequestParam Integer year,
            @Parameter(description = "월") @RequestParam Integer month,
            @Parameter(hidden = true) @IsLogin Long userId) {
        return ResponseCustom.success(fridgeService.getFridgeFoodStatistics(fridgeId, deleteCategory, userId, year, month));
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
    // todo: 토큰 추가하기, 어디에 쓰이는지?
    @GetMapping("/food-lists")
    public ResponseCustom<?> getFridgeUserFoodList(@RequestParam(required = false) Long fridgeId,
                                                   @RequestParam Long userId) {
        return ResponseCustom.success(this.fridgeService.getFridgeUserFoodList(fridgeId, userId));
    }
}
