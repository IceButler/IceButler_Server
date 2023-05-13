package com.example.icebutler_server.purchase.entity;

import com.example.icebutler_server.user.entity.Provider;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Subscription {
  BASIC("기본형", 0),
  PREMIUM("프리미엄", 4900);

  private String name;
  private int price;

  Subscription(String name, int price) {
    this.name = name;
    this.price = price;
  }

  public static Subscription getSubscriptionByName(String name) {
    return Arrays.stream(Subscription.values())
            .filter(f -> f.getName().equals(name))
            .findAny().orElse(null);
  }


}
