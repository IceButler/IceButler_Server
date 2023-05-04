package com.example.icebutler_server.map.exception;

public class NoResponseAPIException extends RuntimeException{
    public NoResponseAPIException(){
        super("naver 지도 api를 불러올 수 없거나, 주소가 잘못되어 있습니다.");
    }
}
