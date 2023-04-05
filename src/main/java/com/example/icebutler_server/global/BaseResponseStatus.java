package com.example.icebutler_server.global;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {

  /**
   * 1000: 요청 성공
   */
  SUCCESS(true, 1000, "요청에 성공하였습니다."),

  /**
   *  2000: Request 오류
   */
  // users(2000~2099)
  NULL_TOKEN(false, 2000, "토큰 값을 입력해주세요."),
  NULL_EMAIL(false, 2001, "이메일을 입력해주세요."),
  NULL_PROVIDER(false, 2002, "소셜 이름을 입력해주세요."),
  INVALID_PROVIDER(false, 2003, "잘못된 소셜 이름입니다."),
  ALREADY_WITHDRAW_USER(false, 2004, "이미 탈퇴한 회원입니다."),
  INVALID_TOKEN(false, 2005, "유효하지 않은 토큰 값입니다."),
  UNSUPPORTED_TOKEN(false, 2006, "잘못된 형식의 토큰 값입니다."),
  MALFORMED_TOKEN(false, 2007, "잘못된 구조의 토큰 값입니다."),

  // fridge(2100~2199)
  NULL_FRIDGE_IDX(false, 2100, "fridgeIdx를 입력해주세요"),
  NULL_FRIDGE_NAME(false, 2101, "냉장고 이름을 입력해주세요"),
  NULL_OWNER_IDX(false, 2102, "onwer를 입력해주세요"),

  // cart(2200~2299)

  // food(2300~2399)

  /**
   *  3000: Response 오류
   */
  // users(3000~3099)
  INVALID_USER_IDX(false, 3000, "사용자를 찾을 수 없습니다."),
  EXIST_NICKNAME(false, 3001, "이미 사용 중인 닉네임입니다."),
  INVALID_EMAIL(false, 3002, "존재하지 않는 이메일입니다."),
  NO_STORE_ROLE(false, 3003, "판매자가 아닙니다."),
  INVALID_USER_STATUS(false, 3004, "비활성화된 사용자입니다."),
  EXPIRED_TOKEN(false, 3005, "만료된 토큰 값입니다."),

  // fridge(3100~3199)
  NULL_SEARCH_FOOD(false, 2103, "검색 결과가 없습니다."),

  // cart(3200~3299)

  // food(3300~3399)

  // cs(3600~3699)
  NO_NOTICE(false, 3600, "공지사항이 없습니다."),
  INVALID_NOTICE_IDX(false, 3601, "존재하지 않는 공지사항 입니다."),

  /**
   * 4000: DB, Server 오류
   */
  DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패했습니다.");

  private final boolean isSuccess;
  private final int code;
  private final String message;

  private BaseResponseStatus(boolean isSuccess, int code, String message) {
    this.isSuccess = isSuccess;
    this.code = code;
    this.message = message;
  }
}
