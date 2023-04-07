package com.example.icebutler_server.fridge.exception;

public class InvalidFridgeUserException extends RuntimeException {
  public InvalidFridgeUserException(){
    super("냉장고 사용자가 아닙니다.");
  }
}
