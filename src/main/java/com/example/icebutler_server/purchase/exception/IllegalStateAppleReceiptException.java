package com.example.icebutler_server.purchase.exception;

public class IllegalStateAppleReceiptException extends RuntimeException {
  public IllegalStateAppleReceiptException(Long status) {
    super("올바르지 않은 apple 영수증 검증 응답입니다. 응답코드 : " + status);
  }
}
