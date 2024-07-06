package com.example.icebutler_server.global.dto.response;

import com.example.icebutler_server.global.exception.ReturnCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class ResponseCustom<T>{

    private String code;
    private String message;
    private int httpStatus;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
    private LocalDateTime timeStamp;

    // 성공
    public ResponseCustom(T data) {
        this.data = data;
        this.timeStamp = LocalDateTime.now();
        this.httpStatus = HttpStatus.OK.value();
        this.message = ReturnCode.SUCCESS.getMessage();
        this.code = ReturnCode.SUCCESS.getCode();
    }

    // 에러
    public ResponseCustom(ReturnCode returnCode) {
        this.data = null;
        this.timeStamp = LocalDateTime.now();
        this.httpStatus = returnCode.status.value();
        this.message = returnCode.getMessage();
        this.code = returnCode.getCode();
    }

    public static <T> ResponseCustom<T> success() {
        return new ResponseCustom<>(null);
    }

    public static <T> ResponseCustom<T> success(@Nullable T data) {
        return new ResponseCustom<>(data);
    }

    public static <T> ResponseCustom<T> error(ReturnCode returnCode) {
        return new ResponseCustom<>(returnCode);
    }
}
