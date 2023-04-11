package com.example.icebutler_server.user.controller;

import com.example.icebutler_server.global.dto.response.ResponseCustom;
import com.example.icebutler_server.user.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class UserExceptionController {
    /**
     * User Exceptions
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseCustom<?> catchUserNotFoundException(UserNotFoundException e) {
        log.error(e.getMessage());
        return ResponseCustom.NOT_FOUND(e.getMessage());
    }
}
