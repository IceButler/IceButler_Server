package com.example.icebutler_server.admin.exception;

public class FoodNotFoundException extends RuntimeException{
  public FoodNotFoundException() {super("해당 id를 가진 음식을 찾을 수 없습니다.");}
}
