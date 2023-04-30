package com.example.icebutler_server.map.exception;

public class NoHeaderException extends RuntimeException{
    public NoHeaderException(){
        super("헤더에 정보값이 없습니다");
    }
}
