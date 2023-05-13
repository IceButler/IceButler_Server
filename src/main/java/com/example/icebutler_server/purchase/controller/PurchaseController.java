package com.example.icebutler_server.purchase.controller;

import com.example.icebutler_server.global.dto.response.ResponseCustom;
import com.example.icebutler_server.global.resolver.Auth;
import com.example.icebutler_server.global.resolver.IsLogin;
import com.example.icebutler_server.global.resolver.LoginStatus;
import com.example.icebutler_server.purchase.dto.request.UserReceiptReq;
import com.example.icebutler_server.purchase.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/purchases")
@RequiredArgsConstructor
public class PurchaseController {

  private final PurchaseService purchaseService;

  // 구매 가능 여부 확인
  @Auth
  @PostMapping("/check")
  public ResponseCustom<?> checkPurchaseHistory(@RequestParam(name = "Subscription") String subscription,
                                                @IsLogin LoginStatus loginStatus) {
    purchaseService.checkPurchaseHistory(subscription, loginStatus.getUserIdx());
    return ResponseCustom.OK();
  }

  // 영수증 검증
  @Auth
  @PostMapping("/purchase")
  public ResponseCustom<?> purchase(@RequestParam(name = "Subscription") String subscription,
                                    @RequestBody UserReceiptReq userReceiptReq,
                                    @IsLogin LoginStatus loginStatus) {
    return ResponseCustom.OK(purchaseService.toUpdatePurchaseHistory(subscription, userReceiptReq, loginStatus.getUserIdx()));
  }

  // 환불

}
