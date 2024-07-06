package com.example.icebutler_server.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum ReturnCode {
    // 성공
    SUCCESS("S0000", HttpStatus.OK, "요청에 성공했습니다."),

    // 서버 에러
    INTERNAL_SERVER_ERROR("E0000", HttpStatus.INTERNAL_SERVER_ERROR, "서버 에레입니다."),
    ;

    public final String code;
    public final HttpStatus status;
    public final String message;

    public static ReturnCode findByCode(String code) {
        return Arrays.stream(ReturnCode.values())
                .filter(r -> r.getCode().equals(code))
                .findAny().orElseThrow(() -> new BaseException(INTERNAL_SERVER_ERROR));
    }
}
