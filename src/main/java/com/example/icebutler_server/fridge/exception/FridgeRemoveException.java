package com.example.icebutler_server.fridge.exception;

public class FridgeRemoveException extends RuntimeException {
  public FridgeRemoveException(){super("올바르지 않은 냉장고 삭제 조건입니다.");}
}
