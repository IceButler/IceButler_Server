package com.example.icebutler_server.purchase.exception;

public class AlreadyPurchaseSubscriptionException extends RuntimeException {
  public AlreadyPurchaseSubscriptionException() {super("이미 가입되어있는 상품이 존재합니다.");}
}
