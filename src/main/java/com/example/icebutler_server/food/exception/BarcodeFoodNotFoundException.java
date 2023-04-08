package com.example.icebutler_server.food.exception;

public class BarcodeFoodNotFoundException extends RuntimeException {
  public BarcodeFoodNotFoundException(){
    super("해당 바코드의 상품을 찾을 수 없습니다.");
  }
}
