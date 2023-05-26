package com.example.icebutler_server.admin.controller;

import com.example.icebutler_server.admin.exception.*;
import com.example.icebutler_server.cart.exception.CartNotFoundException;
import com.example.icebutler_server.global.dto.response.ResponseCustom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class AdminExceptionController {

    @ExceptionHandler(AdminNotFoundException.class)
    public ResponseCustom<Void> adminNotFoundException(AdminNotFoundException e){
        log.error(e.getMessage());
        return ResponseCustom.NOT_FOUND(e.getMessage());
    }

    @ExceptionHandler(FoodNotFoundException.class)
    public ResponseCustom<Void> foodNotFoundException(FoodNotFoundException e){
        log.error(e.getMessage());
        return ResponseCustom.NOT_FOUND(e.getMessage());
    }

    @ExceptionHandler(PasswordNotMatchException.class)
    public ResponseCustom<Void> passwordNotMatchException(PasswordNotMatchException e){
        log.error(e.getMessage());
        return ResponseCustom.BAD_REQUEST(e.getMessage());
    }


    @ExceptionHandler(AlreadyExistEmailException.class)
    public ResponseCustom<Void> alreadyExistEmailException(AlreadyExistEmailException e){
        log.error(e.getMessage());
        return ResponseCustom.BAD_REQUEST(e.getMessage());
    }
}
