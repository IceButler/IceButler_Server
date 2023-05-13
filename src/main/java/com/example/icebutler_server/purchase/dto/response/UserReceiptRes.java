package com.example.icebutler_server.purchase.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class UserReceiptRes {
  private Long status;
  private List<InAppRes> inApp;
}
