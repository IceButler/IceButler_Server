package com.example.icebutler_server.global.exception;

import com.example.icebutler_server.global.dto.response.ResponseCustom;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Objects;

public class ExceptionAdvice {
    @ExceptionHandler(BaseException.class)
    protected ResponseEntity<ResponseCustom> handleBaseException(BaseException e) {
        ReturnCode returnCode = e.getReturnCode();
        return ResponseEntity.status(returnCode.getStatus())
                .body(ResponseCustom.error(returnCode));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseCustom handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        FieldError fieldError = Objects.requireNonNull(e.getFieldError());
        ReturnCode returnCode = ReturnCode.findByCode(fieldError.getDefaultMessage());
        return ResponseCustom.error(returnCode);
    }
}
