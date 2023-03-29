package com.example.icebutler_server.global;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BaseResponseStatus {
    SUCCESS(200, HttpStatus.ACCEPTED, "요청에 성공하였습니다."),

    // common
    REQUEST_ERROR(400, HttpStatus.BAD_REQUEST, "입력값을 확인해주세요."),
    DATABASE_ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR, "서버와의 연결에 실패하였습니다.");

    private final int code;
    private final org.springframework.http.HttpStatus httpStatus;
    private final String message;

}