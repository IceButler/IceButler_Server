package com.example.icebutler_server.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum ReturnCode {
    // 성공
    SUCCESS("S0000", HttpStatus.OK, "요청에 성공했습니다."),

    // Food
    NOT_FOUND_FOOD_CATEGORY("F0000", HttpStatus.NOT_FOUND, "존재하지 않는 카테고리입니다."),
    NOT_FOUND_BARCODE_FOOD("F0001", HttpStatus.NOT_FOUND, "해당 바코드의 상품을 찾을 수 없습니다."),
    ALREADY_EXIST_FOOD_NAME("F0002", HttpStatus.CONFLICT, "중복된 음식 이름입니다."),
    NOT_FOUND_FOOD_DELETE_STATUS("F0003", HttpStatus.NOT_FOUND, "존재하지 않는 식품삭제 타입입니다."),







    // 서버 에러
    INTERNAL_SERVER_ERROR("E0000", HttpStatus.INTERNAL_SERVER_ERROR, "서버 에레입니다."),
    ;

    public final String code;
    public final HttpStatus status;
    public final String message;

    public static ReturnCode findByCode(String code) {
        return Arrays.stream(ReturnCode.values())
                .filter(r -> r.getCode().equals(code))
                .findAny().orElseThrow(() -> new BaseException(INTERNAL_SERVER_ERROR));
    }
}
