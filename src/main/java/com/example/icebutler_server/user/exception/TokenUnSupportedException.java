package com.example.icebutler_server.user.exception;

public class TokenUnSupportedException extends RuntimeException{
    public TokenUnSupportedException(){super("잘못된 형식의 토큰입니다.");}
}
