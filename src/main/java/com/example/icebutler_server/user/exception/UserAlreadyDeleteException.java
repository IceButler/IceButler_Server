package com.example.icebutler_server.user.exception;

public class UserAlreadyDeleteException extends RuntimeException {
    public UserAlreadyDeleteException() {
        super("이미 삭제한 유저입니다.");
    }

}