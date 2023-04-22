package com.example.icebutler_server.fridge.exception;

public class PermissionDeniedException extends RuntimeException {
  public PermissionDeniedException() {super("올바르지 않은 접근 권한입니다.");}
}
