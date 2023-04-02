package com.example.icebutler_server.fridge.exception;

public class CartFoodNotFoundException extends RuntimeException {
    public CartFoodNotFoundException(){
        super("요청한 idx를 가진 CartFood를 찾을 수 없습니다.");
    }
}
