package com.example.icebutler_server.fridge.exception;

public class FridgeTypeNotFoundException extends RuntimeException {
  public FridgeTypeNotFoundException(){super("존재하지 않는 냉장고 유형입니다.");}
}
