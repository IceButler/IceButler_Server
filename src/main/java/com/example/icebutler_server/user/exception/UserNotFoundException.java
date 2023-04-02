package com.example.icebutler_server.user.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(){
        super("요청한 idx를 가진 사용자를 찾을 수 없습니다.");
    }
}
