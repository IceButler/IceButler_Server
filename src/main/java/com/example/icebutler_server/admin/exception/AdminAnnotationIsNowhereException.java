package com.example.icebutler_server.admin.exception;

public class AdminAnnotationIsNowhereException extends RuntimeException {
  public AdminAnnotationIsNowhereException() { super("토큰을 통해 adminId를 추출하는 메서드에는 @Admin 어노테이션을 붙여주세요.");
  }
}
