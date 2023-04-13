package com.example.icebutler_server.user.exception;

public class AlreadyExistNickNameException extends RuntimeException {
  public AlreadyExistNickNameException() {super("이미 존재하는 닉네임입니다.");}
}
