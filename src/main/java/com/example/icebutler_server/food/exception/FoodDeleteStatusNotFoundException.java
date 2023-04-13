package com.example.icebutler_server.food.exception;

public class FoodDeleteStatusNotFoundException extends RuntimeException {
  public FoodDeleteStatusNotFoundException(){
    super("존재하지 않는 음식 삭제 카테고리입니다.");
  }
}
