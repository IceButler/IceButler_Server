package com.example.icebutler_server.food.exception;

public class DuplicateFoodNameException extends RuntimeException {
  public DuplicateFoodNameException() {super("중복된 음식 이름입니다.");}
}
