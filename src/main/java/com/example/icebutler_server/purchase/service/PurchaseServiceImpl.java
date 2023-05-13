package com.example.icebutler_server.purchase.service;

import com.example.icebutler_server.global.util.AppleUtils;
import com.example.icebutler_server.purchase.dto.request.UserReceiptReq;
import com.example.icebutler_server.purchase.dto.response.AppleInAppPurchaseRes;
import com.example.icebutler_server.purchase.dto.response.InAppRes;
import com.example.icebutler_server.purchase.dto.response.UserReceiptRes;
import com.example.icebutler_server.purchase.entity.PurchaseHistory;
import com.example.icebutler_server.purchase.entity.Subscription;
import com.example.icebutler_server.purchase.exception.AlreadyPurchaseSubscriptionException;
import com.example.icebutler_server.purchase.repository.PurchaseRepository;
import com.example.icebutler_server.user.entity.User;
import com.example.icebutler_server.user.exception.AlreadyWithdrawUserException;
import com.example.icebutler_server.user.exception.UserNotFoundException;
import com.example.icebutler_server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PurchaseServiceImpl implements PurchaseService{

  private final PurchaseRepository purchaseRepository;
  private final UserRepository userRepository;
  private final AppleUtils appleUtils;

  @Override
  public void checkPurchaseHistory(String subscription, Long userIdx) {
    User user = userRepository.findByUserIdxAndIsEnable(userIdx, true).orElseThrow(UserNotFoundException::new);
    PurchaseHistory purchaseHistory = purchaseRepository.findByUserAndSubscription(user, Subscription.getSubscriptionByName(subscription));
    if(purchaseHistory != null) throw new AlreadyPurchaseSubscriptionException();
  }

  @Override
  @Transactional
  public Long toUpdatePurchaseHistory(String subscription, UserReceiptReq userReceiptReq, Long userIdx) {
    AppleInAppPurchaseRes appleInAppPurchaseRes = appleUtils.verifyReceipt(userReceiptReq);
    User user = userRepository.findByUserIdxAndIsEnable(userIdx, true).orElseThrow(UserNotFoundException::new);

    PurchaseHistory purchaseHistory = purchaseRepository.findByUserAndSubscription(user, Subscription.getSubscriptionByName(subscription));
    if (purchaseHistory == null) {
      InAppRes inAppInfo = appleInAppPurchaseRes.getReceipt().getInApp().get(0);
      purchaseRepository.save(PurchaseHistory.toEntity(user, Subscription.getSubscriptionByName(subscription), inAppInfo));
    } else throw new AlreadyPurchaseSubscriptionException();

    return appleInAppPurchaseRes.getReceipt().getStatus();
  }
}
