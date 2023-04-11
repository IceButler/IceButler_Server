package com.example.icebutler_server.user.exception;

public class UserEmailMissingValueException extends RuntimeException {
  public UserEmailMissingValueException() {super("사용자 이메일 값을 찾아올 수 없습니다.");}
}
