package com.example.icebutler_server.user.exception;

public class AlreadyWithdrawUserException extends RuntimeException {
  public AlreadyWithdrawUserException() {
    super("이미 탈퇴한 회원입니다.");
  }
}
