package com.example.icebutler_server.user.exception;

public class TokenMalformedException extends RuntimeException{
    public TokenMalformedException(){super("잘못된 구조의 토큰입니다.");}
}
