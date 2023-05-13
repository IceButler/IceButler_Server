package com.example.icebutler_server.user.exception;

public class AccessDeniedUserException extends RuntimeException{
  public AccessDeniedUserException() {super("관리자에 의해 서비스 이용이 제한되었습니다.");}
}
