package com.example.icebutler_server.global.controller;

import com.example.icebutler_server.cart.exception.CartNotFoundException;
import com.example.icebutler_server.food.exception.FoodCategoryNotFoundException;
import com.example.icebutler_server.fridge.exception.FridgeFoodNotFoundException;
import com.example.icebutler_server.fridge.exception.FridgeNameEmptyException;
import com.example.icebutler_server.fridge.exception.FridgeNotFoundException;
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
    public ResponseCustom<Void> catchUserNotFoundException(UserNotFoundException e) {
        log.error(e.getMessage());
        return ResponseCustom.NOT_FOUND(null);
    }

    @ExceptionHandler(FridgeNotFoundException.class)
    public ResponseCustom<Void> catchFridgeNotFoundException(FridgeNotFoundException e) {
        log.error(e.getMessage());
        return ResponseCustom.NOT_FOUND(null);
    }

    @ExceptionHandler(FridgeNameEmptyException.class)
    public ResponseCustom<Void> catchFridgeNameEmptyException(FridgeNameEmptyException e) {
        log.error(e.getMessage());
        return ResponseCustom.NOT_FOUND(null);
    }

    @ExceptionHandler(FoodCategoryNotFoundException.class)
    public ResponseCustom<Void> catchFridgeNotFoundException(FoodCategoryNotFoundException e) {
        log.error(e.getMessage());
        return ResponseCustom.BAD_REQUEST(e.getMessage());

    @ExceptionHandler(FridgeFoodNotFoundException.class)
    public ResponseCustom<Void> catchFridgeNameEmptyException(FridgeFoodNotFoundException e) {
        log.error(e.getMessage());
        return ResponseCustom.NOT_FOUND(e.getMessage());
    }
}
