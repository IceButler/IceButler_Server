package com.example.icebutler_server.food.exception;

public class FoodNameNotFoundException extends RuntimeException {
  public FoodNameNotFoundException() {super("잘못된 음식 이름 입력값입니다.");}
}
