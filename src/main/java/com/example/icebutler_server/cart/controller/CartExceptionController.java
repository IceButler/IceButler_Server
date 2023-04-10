package com.example.icebutler_server.cart.controller;

import com.example.icebutler_server.cart.exception.CartNotFoundException;
import com.example.icebutler_server.global.dto.response.ResponseCustom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CartExceptionController {
    /**
     * Cart Exceptions
     */
    @ExceptionHandler(CartNotFoundException.class)
    public ResponseCustom<Void> catchCartNotFoundException(CartNotFoundException e){
        log.error(e.getMessage());
        return ResponseCustom.NOT_FOUND(null);
    }
}
