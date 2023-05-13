package com.example.icebutler_server.purchase.controller;

import com.example.icebutler_server.global.dto.response.ResponseCustom;
import com.example.icebutler_server.purchase.exception.IllegalStateAppleReceiptException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class PurchaseExceptionController {

  @ExceptionHandler(IllegalStateAppleReceiptException.class)
  public ResponseCustom<?> catchIllegalStateAppleReceiptException(IllegalStateAppleReceiptException e) {
    log.error(e.getMessage());
    return ResponseCustom.NOT_FOUND(e.getMessage());
  }

}
