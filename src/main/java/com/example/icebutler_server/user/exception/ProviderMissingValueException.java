package com.example.icebutler_server.user.exception;

public class ProviderMissingValueException extends RuntimeException {
  public ProviderMissingValueException() {super("부적절한 소셜로그인 provider 입력입니다.");}
}
