package com.example.icebutler_server.global.controller;

import com.example.icebutler_server.cart.exception.CartNotFoundException;
import com.example.icebutler_server.fridge.exception.FridgeNotFoundException;
import com.example.icebutler_server.fridge.exception.InvalidFridgeUserException;
import com.example.icebutler_server.fridge.exception.InvalidFridgeUserRoleException;
import com.example.icebutler_server.user.exception.UserNotFoundException;
import com.example.icebutler_server.global.dto.response.ResponseCustom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(CartNotFoundException.class)
    public ResponseCustom<Void> catchCartNotFoundException(CartNotFoundException e){
        log.error(e.getMessage());
        return ResponseCustom.NOT_FOUND(null);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseCustom<?> catchUserNotFoundException(UserNotFoundException e) {
        log.error(e.getMessage());
        return ResponseCustom.NOT_FOUND(e.getMessage());
    }

    /**
     * Fridge Exceptions
     */
    @ExceptionHandler(FridgeNotFoundException.class)
    public ResponseCustom<?> catchFridgeNotFoundException(FridgeNotFoundException e) {
        log.error(e.getMessage());
        return ResponseCustom.NOT_FOUND(e.getMessage());
    }

    @ExceptionHandler(InvalidFridgeUserException.class)
    public ResponseCustom<?> catchInvalidFridgeUserException(InvalidFridgeUserException e) {
        log.error(e.getMessage());
        return ResponseCustom.FORBIDDEN(e.getMessage());
    }

    @ExceptionHandler(InvalidFridgeUserRoleException.class)
    public ResponseCustom<?> catchInvalidFridgeUserRoleException(InvalidFridgeUserRoleException e) {
        log.error(e.getMessage());
        return ResponseCustom.FORBIDDEN(e.getMessage());
    }
}
