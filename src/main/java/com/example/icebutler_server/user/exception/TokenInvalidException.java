package com.example.icebutler_server.user.exception;

public class TokenInvalidException extends RuntimeException{
    public TokenInvalidException(){super("유효하지 않는 토큰 값입니다.");}
}
