package com.example.icebutler_server.user.entity;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Provider {
  KAKAO(1, "카카오"),
  APPLE(2, "애플");

  private int number;
  private String name;

  Provider(int number, String name) {
    this.number = number;
    this.name = name;
  }

  public static Provider getProviderByName(String name) {
    return Arrays.stream(Provider.values())
            .filter(r -> r.getName().equals(name))
            .findAny().orElse(null);
  }
}
