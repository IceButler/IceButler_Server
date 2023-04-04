package com.example.icebutler_server.fridge.exception;

public class FridgeNameEmptyException extends RuntimeException {
  public FridgeNameEmptyException(){
    super("냉장고 이름를 입력하지 않았습니다.");
  }
}
