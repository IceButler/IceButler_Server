package com.example.icebutler_server.admin.exception;

public class AdminNotFoundException extends RuntimeException{
    public AdminNotFoundException() {
        super("해당하는 이메일을 가진 ADMIN 계정은 존재하지 않습니다.");
    }
}
