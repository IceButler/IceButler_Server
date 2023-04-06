package com.example.icebutler_server.fridge.controller;

import com.example.icebutler_server.fridge.dto.fridge.request.FridgeRegisterReq;
import com.example.icebutler_server.fridge.dto.fridge.request.FridgeModifyReq;
import com.example.icebutler_server.fridge.service.FridgeServiceImpl;
import com.example.icebutler_server.global.dto.response.ResponseCustom;
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
    return ResponseCustom.OK(fridgeService.modifyFridge(fridgeIdx, fridgeModifyReq, loginStatus.getUserIdx()));
  }

  // 냉장고 삭제
  @PatchMapping("/{fridgeIdx}/remove")
  public ResponseCustom<?> removeFridge(@PathVariable(name = "fridgeIdx") Long fridgeIdx,
                                        @IsLogin LoginStatus loginStatus) {
    return ResponseCustom.OK(fridgeService.removeFridge(fridgeIdx, loginStatus.getUserIdx()));
  }

  // 냉장고 식품 전체 조회
  @GetMapping("/{fridgeIdx}/foods")
  public ResponseCustom<?> getFoods(@PathVariable(name = "fridgeIdx") Long fridgeIdx,
                                    @IsLogin LoginStatus loginStatus) {
    return ResponseCustom.OK(fridgeService.getFoods(fridgeIdx, loginStatus.getUserIdx()));
  }

  // 냉장고 내 식품 검색 조회
  @GetMapping("/{fridgeIdx}/search")
  public ResponseCustom<?> findFoodByName(@PathVariable(name = "fridgeIdx") Long fridgeIdx,
                                          @RequestParam(value = "foodName") String foodName,
                                          @IsLogin LoginStatus loginStatus) {
    return ResponseCustom.OK(fridgeService.findFoodByName(fridgeIdx, loginStatus.getUserIdx(), foodName));
  }
}
