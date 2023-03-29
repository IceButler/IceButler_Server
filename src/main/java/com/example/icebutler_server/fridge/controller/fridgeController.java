package com.example.icebutler_server.fridge.controller;

import com.example.icebutler_server.fridge.dto.AddFridgeReq;
import com.example.icebutler_server.fridge.service.FridgeService;
import com.example.icebutler_server.global.dto.response.ResponseCustom;
import com.example.icebutler_server.global.entity.BaseEntity;
import com.example.icebutler_server.global.exception.BaseException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/fridge")
@RestController
@RequiredArgsConstructor
public class fridgeController {

  private final FridgeService fridgeService;

  // 냉장고 추가
  @GetMapping("/add/{owner}")
  public ResponseCustom<?> addFridge(
          @PathVariable(name = "owner") Long owner,
          @RequestBody AddFridgeReq addFridgeReq) {
    try {
      return ResponseCustom.OK(fridgeService.addFridge(owner, addFridgeReq));
    } catch (BaseException e) {
      return ResponseCustom.BAD_REQUEST(e.getStatus());
    }
  }

  // 냉장고 삭제
  @GetMapping("remove/{fridgeId}")
  public ResponseCustom<?> removeFridge(@PathVariable(name = "fridgeId") Long fridgeId) {
    try {
      return ResponseCustom.OK(fridgeService.removeFridge(fridgeId));
    } catch (BaseException e) {
      return ResponseCustom.BAD_REQUEST(e.getStatus());
    }
  }

  // 냉장고 식품 전체 조회
  @GetMapping("foods/{fridgeId}/{owner}")
  public ResponseCustom<?> getFoods(@PathVariable(name = "fridgeId") Long fridgeId,
                                    @PathVariable(name = "owner") Long owner) {
    try {
      return ResponseCustom.OK(fridgeService.getFoods(owner, fridgeId));
    } catch (BaseException e) {
      return ResponseCustom.BAD_REQUEST(e.getStatus());
    }
  }

  // 냉장고 내 식품 검색 조회
  @GetMapping("search/{fridgeId}/{ownerId}")
  public ResponseCustom<?> findFoodByName(@PathVariable(name = "fridgeId") Long fridgeId,
                                          @PathVariable(name = "ownerId") Long ownerId,
                                          @RequestParam(value = "foodName") String foodName) {
    try {
      return ResponseCustom.OK(fridgeService.findFoodByName(fridgeId, ownerId, foodName));
    } catch (BaseException e) {
      return ResponseCustom.BAD_REQUEST(e.getStatus());
    }
  }
}
