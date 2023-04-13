package com.example.icebutler_server.user.exception;

public class InvalidUserNickNameException extends RuntimeException {
  public InvalidUserNickNameException() {
    super("올바르지 않은 닉네임 형식입니다.");
  }
}
