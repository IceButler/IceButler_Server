package com.example.icebutler_server.fridge.controller;

import com.example.icebutler_server.fridge.dto.fridge.request.FridgeFoodReq;
import com.example.icebutler_server.fridge.dto.fridge.request.FridgeRegisterReq;
import com.example.icebutler_server.fridge.dto.fridge.request.FridgeModifyReq;
import com.example.icebutler_server.fridge.service.FridgeServiceImpl;
import com.example.icebutler_server.global.dto.response.ResponseCustom;
import com.example.icebutler_server.global.resolver.Auth;
import com.example.icebutler_server.global.resolver.IsLogin;
import com.example.icebutler_server.global.resolver.LoginStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/fridges")
@RestController
@RequiredArgsConstructor
public class FridgeController {

  private final FridgeServiceImpl fridgeService;

  // 냉장고 추가
  @PostMapping("/register")
  public ResponseCustom<?> registerFridge(@RequestBody FridgeRegisterReq fridgeRegisterReq) {
    return ResponseCustom.OK(fridgeService.registerFridge(fridgeRegisterReq));
  }

  // 냉장고 업데이트
  @PatchMapping("/{fridgeIdx}")
  public ResponseCustom<?> modifyFridge(@PathVariable(name = "fridgeIdx") Long fridgeIdx,
                                        @RequestBody FridgeModifyReq fridgeModifyReq,
                                        @IsLogin LoginStatus loginStatus) {
    fridgeService.modifyFridge(fridgeIdx, fridgeModifyReq, loginStatus.getUserIdx());
    return ResponseCustom.OK();
  }

  // 냉장고 삭제
  @PatchMapping("/{fridgeIdx}/remove")
  public ResponseCustom<?> removeFridge(@PathVariable(name = "fridgeIdx") Long fridgeIdx,
                                        @IsLogin LoginStatus loginStatus) {
    return ResponseCustom.OK(fridgeService.removeFridge(fridgeIdx, loginStatus.getUserIdx()));
  }

  // [Get] 냉장고 식품 전체 조회
  @GetMapping("/{fridgeIdx}/foods")
  public ResponseCustom<?> getFoods(@PathVariable(name = "fridgeIdx") Long fridgeIdx,
                                    @IsLogin LoginStatus loginStatus,
                                    @RequestParam(required = false) String category) {
    return ResponseCustom.OK(fridgeService.getFoods(fridgeIdx, loginStatus.getUserIdx(), category));
  }

  // 냉장고 내 식품 검색 조회
  @GetMapping("/{fridgeIdx}/search")
  public ResponseCustom<?> findFoodByName(@PathVariable(name = "fridgeIdx") Long fridgeIdx,
                                          @RequestParam(value = "foodName") String foodName,
                                          @IsLogin LoginStatus loginStatus) {
    return ResponseCustom.OK(fridgeService.findFoodByName(fridgeIdx, loginStatus.getUserIdx(), foodName));
  }

  // 냉장고 내 식품 상세 조회
  @GetMapping("/{fridgeIdx}/foods/{fridgeFoodIdx}")
  public ResponseCustom<?> getFridgeFood(@PathVariable Long fridgeIdx,
                                         @PathVariable Long fridgeFoodIdx,
                                         @IsLogin LoginStatus loginStatus){
    return ResponseCustom.OK(fridgeService.getFridgeFood(fridgeIdx, fridgeFoodIdx, loginStatus.getUserIdx()));
  }

  // 냉장고 내 식품 추가
  @PostMapping("/{fridgeIdx}/food")
  public ResponseCustom<?> addFridgeFood(@RequestBody FridgeFoodReq fridgeFoodReq,
                                         @PathVariable Long fridgeIdx,
                                         @IsLogin LoginStatus loginStatus){
    fridgeService.addFridgeFood(fridgeFoodReq, fridgeIdx, loginStatus.getUserIdx());
    return ResponseCustom.OK();
  }

  // 냉장고 내 식품 수정
  @PatchMapping("/{fridgeIdx}/foods/{fridgeFoodIdx}")
  public ResponseCustom<?> modifyFridgeFood(@RequestBody FridgeFoodReq fridgeFoodReq,
                                         @PathVariable Long fridgeIdx,
                                         @PathVariable Long fridgeFoodIdx,
                                         @IsLogin LoginStatus loginStatus){
    fridgeService.modifyFridgeFood(fridgeIdx,fridgeFoodIdx, fridgeFoodReq,  loginStatus.getUserIdx());
    return ResponseCustom.OK();
  }

  //냉장고 내 유저 조회
  @GetMapping("{fridgeIdx}/members")
  public ResponseCustom<?> getMembers(
          @PathVariable(name="fridgeIdx") Long fridgeIdx,
          @IsLogin LoginStatus loginStatus
  ){
    return ResponseCustom.OK(fridgeService.searchMembers(fridgeIdx,loginStatus.getUserIdx()));
  }

  //냉장고 선택
  @Auth
  @GetMapping("{fridgeIdx}")
  public ResponseCustom<?> getFridges(
          @PathVariable(name="fridgeIdx") Long fridgeIdx,
          @IsLogin LoginStatus loginStatus
  ){
    return ResponseCustom.OK(fridgeService.getFridges(fridgeIdx,loginStatus.getUserIdx()));
  }

  //마이 냉장고 전체 조회
  @GetMapping("")
  public ResponseCustom<?> myFridge(
//          @PathVariable(name = "fridgeIdx") Long fridgeIdx,
          @IsLogin LoginStatus loginStatus
  ){
    return ResponseCustom.OK(fridgeService.myFridge(loginStatus.getUserIdx()));
  }

}
