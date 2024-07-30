package com.example.icebutler_server.global.exception;

import com.example.icebutler_server.global.dto.response.ResponseCustom;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.sql.SQLException;
import java.time.format.DateTimeParseException;

@RestControllerAdvice
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
        return ResponseCustom.error(ReturnCode.INVALID_PARAM);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(
            value = {
                    HttpMessageNotReadableException.class,
                    HttpRequestMethodNotSupportedException.class,
                    MissingServletRequestParameterException.class,
                    MethodArgumentTypeMismatchException.class,
                    DateTimeParseException.class
            }
    )
    protected ResponseCustom handleBaseException(Exception e) {
        return ResponseCustom.error(ReturnCode.INVALID_PARAM);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(
            value = {SQLException.class}
    )
    protected ResponseCustom handleBaseException(SQLException e) {
        return ResponseCustom.error(ReturnCode.INTERNAL_SERVER_ERROR);
    }
}
