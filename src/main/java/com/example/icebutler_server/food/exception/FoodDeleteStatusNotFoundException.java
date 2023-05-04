package com.example.icebutler_server.food.exception;

public class FoodDeleteStatusNotFoundException extends RuntimeException {
  public FoodDeleteStatusNotFoundException(){
    super("존재하지 않는 식품삭제 타입입니다.");
  }
}
