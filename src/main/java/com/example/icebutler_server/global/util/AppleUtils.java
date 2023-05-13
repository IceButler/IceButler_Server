package com.example.icebutler_server.global.util;

import com.example.icebutler_server.purchase.dto.request.UserReceiptReq;
import com.example.icebutler_server.purchase.dto.response.AppleInAppPurchaseRes;
import com.example.icebutler_server.purchase.exception.IllegalStateAppleReceiptException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class AppleUtils {

  public static String appleProductionUrl;
  public static String appleSandboxUrl;

  @Value("${apple.production.url}")
  public static void setAppleProductionUrl(String value) {
    appleProductionUrl = value;
  }
  @Value("${apple.sandbox.url}")
  public static void setAppleSandboxUrl(String value) {
    appleSandboxUrl = value;
  }

  public AppleInAppPurchaseRes verifyReceipt(UserReceiptReq userReceiptReq) throws IllegalStateException {
    Map<String, String> requestMap = new HashMap<>();
    requestMap.put("receipt-data", userReceiptReq.getReceiptData());

    RestTemplate restTemplate = new RestTemplateBuilder().build();
    ResponseEntity<AppleInAppPurchaseRes> responseEntity = restTemplate.postForEntity(Constant.APPLE_PRODUCTION_URL, requestMap, AppleInAppPurchaseRes.class);
    AppleInAppPurchaseRes appleInAppPurchaseRes = responseEntity.getBody();

    if (appleInAppPurchaseRes != null) {
      Long status = appleInAppPurchaseRes.getReceipt().getStatus();

      // status -> 0 이면 정상 처리
      if (status == 21007) {
        // Test 환경이라면 다시 체크
        responseEntity = restTemplate.postForEntity(appleSandboxUrl, requestMap, AppleInAppPurchaseRes.class);
        appleInAppPurchaseRes = responseEntity.getBody();
      } else if (status != 0) {
        throw new IllegalStateAppleReceiptException(status);
      }
      return appleInAppPurchaseRes;
    }
    return null;
  }

}
