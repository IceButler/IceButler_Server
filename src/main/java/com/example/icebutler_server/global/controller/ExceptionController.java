package com.example.icebutler_server.global.controller;

import com.example.icebutler_server.cart.exception.CartNotFoundException;
import com.example.icebutler_server.food.exception.BarcodeFoodNotFoundException;
import com.example.icebutler_server.food.exception.FoodCategoryNotFoundException;
import com.example.icebutler_server.fridge.exception.FridgeFoodNotFoundException;
import com.example.icebutler_server.fridge.exception.FridgeNotFoundException;
import com.example.icebutler_server.fridge.exception.FridgeUserNotFoundException;
import com.example.icebutler_server.fridge.exception.InvalidFridgeUserRoleException;
import com.example.icebutler_server.user.exception.AlreadyWithdrawUserException;
import com.example.icebutler_server.user.exception.ProviderMissingValueException;
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

    @ExceptionHandler(FoodCategoryNotFoundException.class)
    public ResponseCustom<Void> catchFridgeNotFoundException(FoodCategoryNotFoundException e) {
        log.error(e.getMessage());
        return ResponseCustom.BAD_REQUEST(e.getMessage());
    }

    @ExceptionHandler(FridgeFoodNotFoundException.class)
    public ResponseCustom<Void> catchFridgeNameEmptyException(FridgeFoodNotFoundException e) {
        log.error(e.getMessage());
        return ResponseCustom.NOT_FOUND(e.getMessage());
    }

    @ExceptionHandler(BarcodeFoodNotFoundException.class)
    public ResponseCustom<Void> catchFridgeNotFoundException(BarcodeFoodNotFoundException e) {
        log.error(e.getMessage());
        return ResponseCustom.BAD_REQUEST(e.getMessage());
    }

    /*
     * user Exceptions
     */
    @ExceptionHandler(ProviderMissingValueException.class)
    public ResponseCustom<Void> catchProviderMissingValueException(ProviderMissingValueException e) {
        log.error(e.getMessage());
        return ResponseCustom.NOT_FOUND(e.getMessage());
    }

    @ExceptionHandler(AlreadyWithdrawUserException.class)
    public ResponseCustom<Void> catchAlreadyWithdrawUserException(AlreadyWithdrawUserException e) {
        log.error(e.getMessage());
        return ResponseCustom.FORBIDDEN(e.getMessage());
    }


}
