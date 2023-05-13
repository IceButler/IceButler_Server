package com.example.icebutler_server.purchase.service;

import com.example.icebutler_server.purchase.dto.response.UserReceiptRes;
import com.example.icebutler_server.purchase.dto.request.UserReceiptReq;

public interface PurchaseService {
  void checkPurchaseHistory(String subscription, Long userIdx);
  Long toUpdatePurchaseHistory(String subscription, UserReceiptReq userReceiptReq, Long userIdx);
}
