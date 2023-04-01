package com.example.icebutler_server.fridge.exception;


public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException(){
        super("요청한 idx를 가진 장바구니를 찾을 수 없습니다.");
    }
}
