package com.example.icebutler_server.fridge.exception;

public class FridgeFoodNotFoundException extends RuntimeException {
  public FridgeFoodNotFoundException(){
    super("요청한 idx를 가진 냉장고 내 식품을 찾을 수 없습니다.");
  }
}
