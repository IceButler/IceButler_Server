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

    // Global
    INVALID_PARAM("G0000", HttpStatus.BAD_REQUEST, "잘못된 파라미터입니다."),
    NO_PERMISSION("G0001", HttpStatus.FORBIDDEN, "권한이 없습니다."),

    // Auth
    EXPIRED_TOKEN("A0000", HttpStatus.UNAUTHORIZED, "만료된 토큰입니다. 다시 발급해주세요."),
    NULL_TOKEN("A0001", HttpStatus.BAD_REQUEST, "토큰을 입력해주세요."),

    // Cart
    NOT_FOUND_CART("C0000", HttpStatus.NOT_FOUND, "존재하지 않는 장바구니입니다."),

    // 서버 에러
    INTERNAL_SERVER_ERROR("E0000", HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러입니다."),
    FIREBASE_SERVER_ERROR("E0001", HttpStatus.INTERNAL_SERVER_ERROR, "알림 발송에 실패했습니다."),

    // Food
    INVALID_FOOD_CATEGORY("F0000", HttpStatus.BAD_REQUEST, "존재하지 않는 카테고리입니다."),
    NOT_FOUND_BARCODE_FOOD("F0001", HttpStatus.NOT_FOUND, "해당 바코드의 상품을 찾을 수 없습니다."),
    ALREADY_EXIST_FOOD_NAME("F0002", HttpStatus.CONFLICT, "이미 존재하는 식품명입니다."),
    INVALID_FOOD_DELETE_STATUS("F0003", HttpStatus.BAD_REQUEST, "존재하지 않는 식품삭제 타입입니다."),

    // Fridge(Refrigerator)
    NOT_FOUND_FRIDGE("R0000", HttpStatus.CONFLICT, "존재하지 않는 냉장고입니다."),
    STILL_MEMBER_EXIST("R0001", HttpStatus.CONFLICT, "해당 냉장고에 사용자가 존재합니다."),
    NOT_FOUND_FRIDGE_FOOD("R0002", HttpStatus.NOT_FOUND, "해당 냉장고에 존재하지 않는 식품입니다."),
    NOT_FOUND_FRIDGE_USER("R0003", HttpStatus.NOT_FOUND, "해당 냉장고에 존재하지 않는 사용자입니다."),

    // User
    NOT_FOUND_USER("U0000", HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."),
    ALREADY_WITHDRAWN_USER("U0001", HttpStatus.NOT_FOUND, "이미 탈퇴한 회원입니다."),
    BLOCKED_USER("U0002", HttpStatus.FORBIDDEN, "관리자에 의해 서비스 이용이 제한되었습니다."),
    INVALID_PROVIDER("U0003", HttpStatus.BAD_REQUEST, "부적절한 소셜로그인 provider 입력입니다."),
    INVALID_NICKNAME("U0004", HttpStatus.BAD_REQUEST, "올바르지 않은 닉네임 형식입니다."),
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
