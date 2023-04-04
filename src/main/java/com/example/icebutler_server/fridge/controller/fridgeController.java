package com.example.icebutler_server.fridge.controller;

import com.example.icebutler_server.fridge.dto.request.FridgeRegisterReq;
import com.example.icebutler_server.fridge.dto.request.FridgeModifyReq;
import com.example.icebutler_server.fridge.service.FridgeServiceImpl;
import com.example.icebutler_server.global.dto.response.ResponseCustom;
import com.example.icebutler_server.global.resolver.IsLogin;
import com.example.icebutler_server.global.resolver.LoginStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/fridge")
@RestController
@RequiredArgsConstructor
public class fridgeController {

  private final FridgeServiceImpl fridgeService;

  // 냉장고 추가
  @PostMapping("/register")
  public ResponseCustom<?> registerFridge(@RequestBody FridgeRegisterReq fridgeRegisterReq) {
    return ResponseCustom.OK(fridgeService.registerFridge(fridgeRegisterReq));
  }

  // 냉장고 업데이트
  @PatchMapping("/modify")
  public ResponseCustom<?> modifyFridge(@RequestBody FridgeModifyReq fridgeModifyReq,
                                        @IsLogin LoginStatus loginStatus) {
    return ResponseCustom.OK(fridgeService.modifyFridge(fridgeModifyReq, loginStatus.getUserIdx()));
  }

  // 냉장고 삭제
  @PatchMapping("/remove/{fridgeId}")
  public ResponseCustom<?> removeFridge(@PathVariable(name = "fridgeId") Long fridgeId,
                                        @IsLogin LoginStatus loginStatus) {
    return ResponseCustom.OK(fridgeService.removeFridge(fridgeId, loginStatus.getUserIdx()));
  }

  // 냉장고 식품 전체 조회
  @GetMapping("/foods/{fridgeId}")
  public ResponseCustom<?> getFoods(@PathVariable(name = "fridgeId") Long fridgeId,
                                    @IsLogin LoginStatus loginStatus) {
    return ResponseCustom.OK(fridgeService.getFoods(fridgeId, loginStatus.getUserIdx()));
  }

  // 냉장고 내 식품 검색 조회
  @GetMapping("/search/{fridgeId}")
  public ResponseCustom<?> findFoodByName(@PathVariable(name = "fridgeId") Long fridgeId,
                                          @RequestParam(value = "foodName") String foodName,
                                          @IsLogin LoginStatus loginStatus) {
    return ResponseCustom.OK(fridgeService.findFoodByName(fridgeId, loginStatus.getUserIdx(), foodName));
  }
}
