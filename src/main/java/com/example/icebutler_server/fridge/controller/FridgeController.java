package com.example.icebutler_server.fridge.controller;

import com.example.icebutler_server.fridge.dto.fridge.request.*;
import com.example.icebutler_server.fridge.dto.fridge.response.FridgeFoodsRes;
import com.example.icebutler_server.fridge.dto.fridge.response.FridgeMainRes;
import com.example.icebutler_server.fridge.dto.fridge.response.RecipeFridgeFoodListsRes;
import com.example.icebutler_server.fridge.exception.FridgeTypeNotFoundException;
import com.example.icebutler_server.fridge.service.FridgeServiceImpl;
import com.example.icebutler_server.fridge.service.MultiFridgeServiceImpl;
import com.example.icebutler_server.global.dto.response.ResponseCustom;
import com.example.icebutler_server.global.resolver.Auth;
import com.example.icebutler_server.global.resolver.IsLogin;
import com.example.icebutler_server.global.resolver.LoginStatus;
import com.example.icebutler_server.global.util.Constant;
import lombok.RequiredArgsConstructor;
import okhttp3.Response;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/fridges")
@RestController
@RequiredArgsConstructor
public class FridgeController {

  private final FridgeServiceImpl fridgeService;
  private final MultiFridgeServiceImpl multiFridgeService;

  @GetMapping("/health")
  public ResponseCustom<Void> healthCheck() {
    return ResponseCustom.OK();
  }

  // 냉장고 추가
  @Auth
  @PostMapping("/register")
  public ResponseCustom<?> registerFridge(@RequestBody FridgeRegisterReq fridgeRegisterReq,
                                          @RequestParam(value = "fridgeType") String fridgeType,
                                          @IsLogin LoginStatus loginStatus) {
    if(fridgeType.equals(Constant.FRIDGE)){
      return ResponseCustom.OK(fridgeService.registerFridge(fridgeRegisterReq, loginStatus.getUserIdx()));
    } else if(fridgeType.equals(Constant.MULTI_FRIDGE)){
      return ResponseCustom.OK(multiFridgeService.registerFridge(fridgeRegisterReq, loginStatus.getUserIdx()));
    } else {
      throw new FridgeTypeNotFoundException();
    }
  }

  // 냉장고 업데이트
  @Auth
  @PatchMapping("/{fridgeIdx}")
  public ResponseCustom<?> modifyFridge(@PathVariable(name = "fridgeIdx") Long fridgeIdx,
                                        @RequestBody FridgeModifyReq fridgeModifyReq,
                                        @IsLogin LoginStatus loginStatus) {
    fridgeService.modifyFridge(fridgeIdx, fridgeModifyReq, loginStatus.getUserIdx());
    return ResponseCustom.OK();
  }

  // 냉장고 삭제
  @Auth
  @PatchMapping("/{fridgeIdx}/remove")
  public ResponseCustom<?> removeFridge(@PathVariable(name = "fridgeIdx") Long fridgeIdx,
                                        @IsLogin LoginStatus loginStatus) {
    return ResponseCustom.OK(fridgeService.removeFridge(fridgeIdx, loginStatus.getUserIdx()));
  }

  @Auth
  @PatchMapping("/{fridgeIdx}/remove/each")
  public ResponseCustom<?> removeFridgeUser(@PathVariable(name = "fridgeIdx") Long fridgeIdx,
                                            @IsLogin LoginStatus loginStatus) {
    return ResponseCustom.OK(fridgeService.removeFridgeUser(fridgeIdx, loginStatus.getUserIdx()));
  }

  // [Get] 냉장고 식품 전체 조회
  @Auth
  @GetMapping("/{fridgeIdx}/foods")
  public ResponseCustom<FridgeMainRes> getFoods(@PathVariable(name = "fridgeIdx") Long fridgeIdx,
                                                @IsLogin LoginStatus loginStatus,
                                                @RequestParam(required = false) String category) {
    return ResponseCustom.OK(fridgeService.getFoods(fridgeIdx, loginStatus.getUserIdx(), category));
  }

  // 냉장고 내 식품 검색 조회
  @Auth
  @GetMapping("/{fridgeIdx}/search")
  public ResponseCustom<List<FridgeFoodsRes>> searchFridgeFood(@PathVariable(name = "fridgeIdx") Long fridgeIdx,
                                                               @RequestParam(value = "keyword") String keyword,
                                                               @IsLogin LoginStatus loginStatus) {
    return ResponseCustom.OK(fridgeService.searchFridgeFood(fridgeIdx, loginStatus.getUserIdx(), keyword));
  }

  // 냉장고 내 식품 상세 조회
  @Auth
  @GetMapping("/{fridgeIdx}/foods/{fridgeFoodIdx}")
  public ResponseCustom<?> getFridgeFood(@PathVariable Long fridgeIdx,
                                         @PathVariable Long fridgeFoodIdx,
                                         @IsLogin LoginStatus loginStatus) {
    return ResponseCustom.OK(fridgeService.getFridgeFood(fridgeIdx, fridgeFoodIdx, loginStatus.getUserIdx()));
  }

  // 냉장고 내 식품 추가
  @Auth
  @PostMapping("/{fridgeIdx}/food")
  public ResponseCustom<?> addFridgeFood(@RequestBody FridgeFoodsReq fridgeFoodsReq,
                                         @PathVariable Long fridgeIdx,
                                         @IsLogin LoginStatus loginStatus) {
    fridgeService.addFridgeFood(fridgeFoodsReq, fridgeIdx, loginStatus.getUserIdx());
    return ResponseCustom.OK();
  }

  // 냉장고 내 식품 수정
  @Auth
  @PatchMapping("/{fridgeIdx}/foods/{fridgeFoodIdx}")
  public ResponseCustom<?> modifyFridgeFood(@RequestBody FridgeFoodReq fridgeFoodReq,
                                            @PathVariable Long fridgeIdx,
                                            @PathVariable Long fridgeFoodIdx,
                                            @IsLogin LoginStatus loginStatus) {
    fridgeService.modifyFridgeFood(fridgeIdx, fridgeFoodIdx, fridgeFoodReq, loginStatus.getUserIdx());
    return ResponseCustom.OK();
  }

  // 냉장고 내 식품 삭제(폐기/섭취)
  @Auth
  @DeleteMapping("/{fridgeIdx}/foods")
  public ResponseCustom<?> deleteFridgeFood(@RequestBody DeleteFridgeFoodsReq deleteFridgeFoodsReq,
                                            @RequestParam String type,
                                            @PathVariable Long fridgeIdx,
                                            @IsLogin LoginStatus loginStatus) {
    fridgeService.deleteFridgeFood(deleteFridgeFoodsReq, type, fridgeIdx, loginStatus.getUserIdx());
    return ResponseCustom.OK();
  }

  //냉장고 내 유저 조회
  @Auth
  @GetMapping("{fridgeIdx}/members")
  public ResponseCustom<?> getMembers(
          @PathVariable(name = "fridgeIdx") Long fridgeIdx,
          @IsLogin LoginStatus loginStatus
  ) {
    return ResponseCustom.OK(fridgeService.searchMembers(fridgeIdx, loginStatus.getUserIdx()));
  }

  //냉장고 선택
  @Auth
  @GetMapping("/select")
  public ResponseCustom<?> selectFridges(
          @IsLogin LoginStatus loginStatus
  ) {
    return ResponseCustom.OK(fridgeService.selectFridges(loginStatus.getUserIdx()));
  }

  //마이 냉장고 전체 조회
  @Auth
  @GetMapping("")
  public ResponseCustom<?> myFridge(
//          @PathVariable(name = "fridgeIdx") Long fridgeIdx,
          @IsLogin LoginStatus loginStatus
  ) {
    return ResponseCustom.OK(fridgeService.myFridge(loginStatus.getUserIdx()));
  }

  /**
   * [Get] 냉장고 통계 (낭비/소비)
   */
  @Auth
  @GetMapping("/{fridgeIdx}/statistics")
  public ResponseCustom<?> getFridgeFoodStatistics(@PathVariable(name = "fridgeIdx") Long fridgeIdx,
                                                   @RequestParam String deleteCategory,
                                                   @RequestParam Integer year,
                                                   @RequestParam Integer month,
                                                   @IsLogin LoginStatus status){
    return ResponseCustom.OK(fridgeService.getFridgeFoodStatistics(fridgeIdx, deleteCategory, status.getUserIdx(), year, month));
  }


  // 알림
  @Scheduled(cron = "0/10 * * * * ?")
  public void notifyFridgeFood() {
    fridgeService.notifyFridgeFood();
    multiFridgeService.notifyFridgeFood();
  }

  // 레시피 정보 전달 api

  /**
   * [Get] 사용자가 속한 가정용/공용 냉장고 food list
   */
  // todo: 토큰 추가하기
  @GetMapping("/food-lists")
  public ResponseCustom<?> getFridgeUserFoodList(@RequestParam(name = "fridgeIdx", required = false) Long fridgeIdx,
                                                                        @RequestParam(name = "multiFridgeIdx", required = false) Long multiFridgeIdx,
                                                                        @RequestParam(name = "userIdx") Long userIdx){
    if(fridgeIdx != null && multiFridgeIdx == null){
      return ResponseCustom.OK(this.fridgeService.getFridgeUserFoodList(fridgeIdx, userIdx));
    }else if(multiFridgeIdx != null && fridgeIdx == null) {
      return ResponseCustom.OK(this.multiFridgeService.getFridgeUserFoodList(multiFridgeIdx, userIdx));
    }else throw new FridgeTypeNotFoundException();

  }
}
