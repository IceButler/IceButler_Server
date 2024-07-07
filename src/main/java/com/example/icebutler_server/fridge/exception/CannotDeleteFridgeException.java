package com.example.icebutler_server.fridge.exception;

public class CannotDeleteFridgeException extends RuntimeException {
    public CannotDeleteFridgeException() { super("해당 냉장고에 멤버가 있어서 삭제할 수 없습니다.");
    }
}
