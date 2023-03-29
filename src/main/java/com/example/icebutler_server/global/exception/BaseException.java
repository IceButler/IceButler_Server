package com.example.icebutler_server.global.exception;

import com.example.icebutler_server.global.BaseResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BaseException extends Exception {
  private BaseResponseStatus status;
}
