package com.example.icebutler_server.fridge.exception;

public class InvalidFridgeUserRoleException extends RuntimeException {
  public InvalidFridgeUserRoleException(){
    super("냉장고를 생성한 유저가 아닙니다.");
  }
}
