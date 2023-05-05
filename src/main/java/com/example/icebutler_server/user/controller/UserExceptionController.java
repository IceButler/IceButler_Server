package com.example.icebutler_server.user.controller;

import com.example.icebutler_server.global.dto.response.ResponseCustom;
import com.example.icebutler_server.user.exception.*;
import io.jsonwebtoken.ExpiredJwtException;
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

    @ExceptionHandler(UserNicknameNotFoundException.class)
    public ResponseCustom<?> catchUserNicknameNotFoundException(UserNicknameNotFoundException e) {
        log.error(e.getMessage());
        return ResponseCustom.NOT_FOUND(e.getMessage());
    }

    @ExceptionHandler(CannotDeleteFridgeException.class)
    public ResponseCustom<?> CannotDeleteFridgeException(CannotDeleteFridgeException e) {
        log.error(e.getMessage());
        return ResponseCustom.NOT_FOUND(e.getMessage());
    }

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

    @ExceptionHandler(InvalidUserNickNameException.class)
    public ResponseCustom<?> catchInvalidUserNickNameException(InvalidUserNickNameException e) {
        log.error(e.getMessage());
        return ResponseCustom.BAD_REQUEST(e.getMessage());
    }

    @ExceptionHandler(InvalidUserProfileImgKeyException.class)
    public ResponseCustom<?> catchInvalidUserProfileException(InvalidUserProfileImgKeyException e) {
        log.error(e.getMessage());
        return ResponseCustom.BAD_REQUEST(e.getMessage());
    }

    @ExceptionHandler(TokenExpirationException.class)
    public ResponseCustom<?> catchTokenExpirationException(TokenExpirationException e) {
        log.error(e.getMessage());
        return ResponseCustom.BAD_REQUEST(e.getMessage());
    }

    @ExceptionHandler(AuthAnnotationIsNowhereException.class)
    public ResponseCustom<?> catchAuthAnnotationIsNowhereException(AuthAnnotationIsNowhereException e) {
        log.error(e.getMessage());
        return ResponseCustom.BAD_REQUEST(e.getMessage());
    }
}
