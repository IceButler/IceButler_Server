package com.example.icebutler_server.purchase.dto.response;

import lombok.Getter;

import java.util.Date;

@Getter
public class InAppRes {
  private int quantity;
  private String productId;
  private String transactionId;
  private Date purchaseDate;
  private boolean isTrialPeriod;
}
