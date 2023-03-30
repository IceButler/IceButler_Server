package com.example.icebutler_server.global.dto.response;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class ResponseCustom<T>{

    private T data;
    private LocalDateTime transaction_time;
    private HttpStatus status;
    private String description;
    private int statusCode;


    // OK
    public static <T> ResponseCustom<T> CREATED(@Nullable T data) {
        return (ResponseCustom<T>) ResponseCustom.builder()
                .transaction_time(LocalDateTime.now())
                .status(HttpStatus.OK)
                .data(data)
                .build();
    }

    public static <T> ResponseCustom<T> OK(@Nullable T data) {
        return (ResponseCustom<T>) ResponseCustom.builder()
                .transaction_time(LocalDateTime.now())
                .status(HttpStatus.OK)
                .data(data)
                .build();
    }

    public static <T> ResponseCustom<T> OK() {
        return (ResponseCustom<T>) ResponseCustom.builder()
                .transaction_time(LocalDateTime.now())
                .status(HttpStatus.OK)
                .build();
    }

    public static <T> ResponseCustom<T> BAD_REQUEST(@Nullable String description){
        return (ResponseCustom<T>) ResponseCustom.builder()
                .transaction_time(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST)
                .description(description)
                .build();
    }

    public static <T> ResponseCustom<T> BAD_REQUEST(@Nullable T data){
        return (ResponseCustom<T>) ResponseCustom.builder()
                .transaction_time(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST)
                .data(data)
                .build();
    }

    public static <T> ResponseCustom<T> FORBIDDEN(){
        return (ResponseCustom<T>) ResponseCustom.builder()
                .transaction_time(LocalDateTime.now())
                .status(HttpStatus.FORBIDDEN)
                .build();
    }

    public static <T> ResponseCustom<T> FORBIDDEN(String description){
        return (ResponseCustom<T>) ResponseCustom.builder()
                .transaction_time(LocalDateTime.now())
                .status(HttpStatus.FORBIDDEN)
                .description(description)
                .build();
    }


    public static <T> ResponseCustom<T> UNAUTHORIZED(){
        return (ResponseCustom<T>) ResponseCustom.builder()
                .transaction_time(LocalDateTime.now())
                .status(HttpStatus.UNAUTHORIZED)
                .build();
    }

    public static <T> ResponseCustom<T> INTERNAL_SERVER_ERROR(){
        return (ResponseCustom<T>) ResponseCustom.builder()
                .transaction_time(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
    }

    public static <T> ResponseCustom<T> INTERNAL_SERVER_ERROR(String description){
        return (ResponseCustom<T>) ResponseCustom.builder()
                .transaction_time(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .description(description)
                .build();
    }

    public static <T> ResponseCustom<T> JWT_EXPIRED(){
        return (ResponseCustom<T>) ResponseCustom.builder()
                .transaction_time(LocalDateTime.now())
                .description("JWT_EXPIRED")
                .statusCode(441)
                .build();
    }
}
