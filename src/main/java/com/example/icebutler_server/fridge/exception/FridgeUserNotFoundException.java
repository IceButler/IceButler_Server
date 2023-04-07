package com.example.icebutler_server.fridge.exception;

public class FridgeUserNotFoundException extends RuntimeException {
  public FridgeUserNotFoundException(){
    super("냉장고의 멤버가 아닙니다.");
  }
}
