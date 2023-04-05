package com.example.icebutler_server.fridge.exception;

public class FridgeNotFoundException extends RuntimeException {
  public FridgeNotFoundException(){
    super("요청한 idx를 가진 냉장고를 찾을 수 없습니다.");
  }
}
