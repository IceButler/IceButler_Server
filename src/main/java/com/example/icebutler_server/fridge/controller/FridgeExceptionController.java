package com.example.icebutler_server.fridge.controller;

import com.example.icebutler_server.fridge.exception.FridgeFoodNotFoundException;
import com.example.icebutler_server.fridge.exception.FridgeNotFoundException;
import com.example.icebutler_server.fridge.exception.FridgeUserNotFoundException;
import com.example.icebutler_server.fridge.exception.InvalidFridgeUserRoleException;
import com.example.icebutler_server.global.dto.response.ResponseCustom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class FridgeExceptionController {
    /**
     * Fridge Exceptions
     */
    @ExceptionHandler(FridgeNotFoundException.class)
    public ResponseCustom<?> catchFridgeNotFoundException(FridgeNotFoundException e) {
        log.error(e.getMessage());
        return ResponseCustom.NOT_FOUND(e.getMessage());
    }

    @ExceptionHandler(FridgeFoodNotFoundException.class)
    public ResponseCustom<Void> catchFridgeNameEmptyException(FridgeFoodNotFoundException e) {
        log.error(e.getMessage());
        return ResponseCustom.NOT_FOUND(e.getMessage());
    }

    /**
     * FridgeUser Exceptions
     */
    @ExceptionHandler(FridgeUserNotFoundException.class)
    public ResponseCustom<?> catchInvalidFridgeUserException(FridgeUserNotFoundException e) {
        log.error(e.getMessage());
        return ResponseCustom.FORBIDDEN(e.getMessage());
    }

    @ExceptionHandler(InvalidFridgeUserRoleException.class)
    public ResponseCustom<?> catchInvalidFridgeUserRoleException(InvalidFridgeUserRoleException e) {
        log.error(e.getMessage());
        return ResponseCustom.FORBIDDEN(e.getMessage());
    }
}
