package com.example.icebutler_server.fridge.exception;

public class FridgeRemoveException extends RuntimeException {
  public FridgeRemoveException(){super("냉장고에 가입되어있는 멤버가 존재합니다.");}
}
