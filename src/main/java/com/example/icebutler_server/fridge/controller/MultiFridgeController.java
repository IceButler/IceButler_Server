package com.example.icebutler_server.fridge.controller;

import com.example.icebutler_server.fridge.dto.fridge.request.DeleteFridgeFoodsReq;
import com.example.icebutler_server.fridge.dto.fridge.request.FridgeFoodReq;
import com.example.icebutler_server.fridge.dto.fridge.request.FridgeFoodsReq;
import com.example.icebutler_server.fridge.dto.fridge.request.FridgeModifyReq;
import com.example.icebutler_server.fridge.dto.fridge.response.FridgeFoodsRes;
import com.example.icebutler_server.fridge.dto.fridge.response.FridgeMainRes;
import com.example.icebutler_server.fridge.dto.fridge.response.SearchFoodRes;
import com.example.icebutler_server.fridge.dto.fridge.response.SearchFridgeFoodRes;
import com.example.icebutler_server.fridge.service.MultiFridgeServiceImpl;
import com.example.icebutler_server.global.dto.response.ResponseCustom;
import com.example.icebutler_server.global.resolver.Auth;
import com.example.icebutler_server.global.resolver.IsLogin;
import com.example.icebutler_server.global.resolver.LoginStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequestMapping("/multiFridges")
@RestController
@RequiredArgsConstructor
public class MultiFridgeController {
    private final MultiFridgeServiceImpl multiFridgeService;

    /**
     * [patch] 냉장고 수정
     */
    @Auth
    @PatchMapping("/{multiFridgeIdx}")
    @ResponseBody
    public ResponseCustom<?> modifyFridge(@PathVariable(name = "multiFridgeIdx") Long multiFridgeIdx,
                                                 @RequestBody FridgeModifyReq fridgeModifyReq,
                                                 @IsLogin LoginStatus loginStatus) {
        this.multiFridgeService.modifyFridge(multiFridgeIdx, fridgeModifyReq, loginStatus.getUserIdx());
        return ResponseCustom.OK();
    }

    /**
     * [post] 냉장고 식품 추가
     */
    @Auth
    @PostMapping("/{multiFridgeIdx}/food")
    public ResponseCustom<?> addFridgeFood(@RequestBody FridgeFoodsReq fridgeFoodsReq,
                                           @PathVariable(name = "multiFridgeIdx") Long multiFridgeIdx,
                                           @IsLogin LoginStatus loginStatus){
        this.multiFridgeService.addFridgeFood(fridgeFoodsReq, multiFridgeIdx, loginStatus.getUserIdx());
        return ResponseCustom.OK();
    }
    /**
     * [Get] 냉장고 식품 전체 조회
     */
    @Auth
    @GetMapping("/{multiFridgeIdx}/foods")
    public ResponseCustom<FridgeMainRes> getFoods(@PathVariable(name = "multiFridgeIdx") Long multiFridgeIdx,
                                                  @IsLogin LoginStatus loginStatus,
                                                  @RequestParam(required = false) String category) {
        return ResponseCustom.OK(multiFridgeService.getFoods(multiFridgeIdx, loginStatus.getUserIdx(), category));
    }

    /**
     * [Patch] 냉장고식품수정
     */
    @Auth
    @PatchMapping("/{multiFridgeIdx}/foods/{multiFridgeFoodIdx}")
    public ResponseCustom<?> modifyFridgeFood(@PathVariable(name = "multiFridgeIdx") Long multiFridgeIdx,
                                      @PathVariable(name = "multiFridgeFoodIdx") Long multiFridgeFoodIdx,
                                      @IsLogin LoginStatus loginStatus,
                                      @RequestBody FridgeFoodReq fridgeFoodReq) {
        this.multiFridgeService.modifyFridgeFood(multiFridgeIdx, multiFridgeFoodIdx, fridgeFoodReq, loginStatus.getUserIdx());
        return ResponseCustom.OK();
    }

    /**
     * [Get] 냉장고 통계 (낭비/소비)
     */
    @Auth
    @GetMapping("/{multiFridgeIdx}/statistics")
    public ResponseCustom<?> getFridgeFoodStatistics(@PathVariable(name = "multiFridgeIdx") Long multiFridgeIdx,
                                                 @RequestParam String deleteCategory,
                                                 @RequestParam Integer year,
                                                 @RequestParam Integer month,
                                                 @IsLogin LoginStatus status){
        return ResponseCustom.OK(this.multiFridgeService.getFridgeFoodStatistics(multiFridgeIdx, deleteCategory, status.getUserIdx(), year, month));
    }

    // 냉장고 내 식품 상세 조회
    @Auth
    @GetMapping("/{multiFridgeIdx}/foods/{multiFridgeFoodIdx}")
    public ResponseCustom<?> getFridgeFood(@PathVariable Long multiFridgeIdx,
                                           @PathVariable Long multiFridgeFoodIdx,
                                           @IsLogin LoginStatus loginStatus){
        return ResponseCustom.OK(this.multiFridgeService.getFridgeFood(multiFridgeIdx, multiFridgeFoodIdx, loginStatus.getUserIdx()));
    }

    //냉장고 내 유저 조회
    @Auth
    @GetMapping("{multiFridgeIdx}/members")
    public ResponseCustom<?> getMembers(
            @PathVariable(name = "multiFridgeIdx") Long multiFridgeIdx,
            @IsLogin LoginStatus loginStatus
    ) {
        return ResponseCustom.OK(multiFridgeService.searchMembers(multiFridgeIdx, loginStatus.getUserIdx()));
    }

    // 냉장고 삭제
    @Auth
    @DeleteMapping("/{multiFridgeIdx}")
    public ResponseCustom<Long> removeFridge(@PathVariable(name = "multiFridgeIdx") Long fridgeIdx,
                                          @IsLogin LoginStatus loginStatus) {
        return ResponseCustom.OK(multiFridgeService.removeFridge(fridgeIdx, loginStatus.getUserIdx()));
    }

    @Auth
    @DeleteMapping("/{multiFridgeIdx}/each")
    public ResponseCustom<?> removeFridgeUser(@PathVariable(name = "multiFridgeIdx") Long fridgeIdx,
                                              @IsLogin LoginStatus loginStatus) {
        return ResponseCustom.OK(multiFridgeService.removeFridgeUser(fridgeIdx, loginStatus.getUserIdx()));
    }

    // 공용냉장고 내 식품 삭제(폐기/섭취)
    @Auth
    @DeleteMapping("/{multiFridgeIdx}/foods")
    public ResponseCustom<?> deleteFridgeFood(@RequestBody DeleteFridgeFoodsReq deleteFridgeFoodsReq,
                                              @RequestParam String type,
                                              @PathVariable Long multiFridgeIdx,
                                              @IsLogin LoginStatus loginStatus) {
        multiFridgeService.deleteFridgeFood(deleteFridgeFoodsReq, type, multiFridgeIdx, loginStatus.getUserIdx());
        return ResponseCustom.OK();
    }

    // 공용 냉장고 내 식품 검색 조회
    @Auth
    @GetMapping("/{multiFridgeIdx}/search")
    public ResponseCustom<List<FridgeFoodsRes>> searchFridgeFood(@PathVariable(name = "multiFridgeIdx") Long fridgeIdx,
                                                                 @RequestParam(value = "keyword") String keyword,
                                                                 @IsLogin LoginStatus loginStatus) {
        return ResponseCustom.OK(multiFridgeService.searchFridgeFood(fridgeIdx, loginStatus.getUserIdx(), keyword));
    }
}
