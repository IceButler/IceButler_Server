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

    // Auth
    EXPIRED_TOKEN("A0001", HttpStatus.UNAUTHORIZED, "만료된 토큰입니다. 다시 발급해주세요."),



    // User
    INVALID_PROVIDER("U0000", HttpStatus.BAD_REQUEST, "부적절한 소셜로그인 provider 입력입니다."),
    UNAUTHORIZED_USER("U0001", HttpStatus.UNAUTHORIZED, "관리자에 의해 서비스 이용이 제한되었습니다."),
    NOT_FOUND_EMAIL("U0002", HttpStatus.NOT_FOUND, "사용자 이메일 값을 찾아올 수 없습니다."),
    ALREADY_WITHDRAWN_USER("U0003", HttpStatus.NOT_FOUND, "이미 탈퇴한 회원입니다."),
    INVALID_NICKNAME("U0004", HttpStatus.BAD_REQUEST, "올바르지 않은 닉네임 형식입니다."),
    NOT_FOUND_USER("U0005", HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다."),


    // Food
    NOT_FOUND_FOOD_CATEGORY("F0000", HttpStatus.NOT_FOUND, "존재하지 않는 카테고리입니다."),
    NOT_FOUND_BARCODE_FOOD("F0001", HttpStatus.NOT_FOUND, "해당 바코드의 상품을 찾을 수 없습니다."),
    ALREADY_EXIST_FOOD_NAME("F0002", HttpStatus.CONFLICT, "중복된 음식 이름입니다."),
    NOT_FOUND_FOOD_DELETE_STATUS("F0003", HttpStatus.NOT_FOUND, "존재하지 않는 식품삭제 타입입니다."),

    // Cart
    NOT_FOUND_CART("C0000", HttpStatus.NOT_FOUND, "장바구니를 찾을 수 없습니다."),







    // 서버 에러
    INTERNAL_SERVER_ERROR("E0000", HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러입니다."),
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
