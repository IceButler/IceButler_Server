package com.example.icebutler_server.food.controller;

import com.example.icebutler_server.admin.exception.FoodNotFoundException;
import com.example.icebutler_server.food.exception.BarcodeFoodNotFoundException;
import com.example.icebutler_server.food.exception.FoodCategoryNotFoundException;
import com.example.icebutler_server.food.exception.FoodDeleteStatusNotFoundException;
import com.example.icebutler_server.food.exception.FoodNameNotFoundException;
import com.example.icebutler_server.global.dto.response.ResponseCustom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class FoodExceptionController {
    /**
     * Food Exceptions
     */
    @ExceptionHandler(FoodCategoryNotFoundException.class)
    public ResponseCustom<Void> catchFoodCategoryNotFoundException(FoodCategoryNotFoundException e) {
        log.error(e.getMessage());
        return ResponseCustom.BAD_REQUEST(e.getMessage());
    }

    @ExceptionHandler(BarcodeFoodNotFoundException.class)
    public ResponseCustom<Void> catchBarcodeFoodNotFoundException(BarcodeFoodNotFoundException e) {
        log.error(e.getMessage());
        return ResponseCustom.BAD_REQUEST(e.getMessage());
    }

    @ExceptionHandler(FoodDeleteStatusNotFoundException.class)
    public ResponseCustom<Void> catchFoodDeleteStatusNotFoundException(FoodDeleteStatusNotFoundException e) {
        log.error(e.getMessage());
        return ResponseCustom.BAD_REQUEST(e.getMessage());
    }

    @ExceptionHandler(FoodNotFoundException.class)
    public ResponseCustom<?> catchFoodNotFoundException(FoodNotFoundException e) {
        log.error(e.getMessage());
        return ResponseCustom.NOT_FOUND(e.getMessage());
    }

    @ExceptionHandler(FoodNameNotFoundException.class)
    public ResponseCustom<?> catchFoodNameNotFoundException(FoodNameNotFoundException e) {
        log.error(e.getMessage());
        return ResponseCustom.NOT_FOUND(e.getMessage());
    }
}
