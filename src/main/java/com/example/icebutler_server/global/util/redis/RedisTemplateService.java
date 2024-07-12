package com.example.icebutler_server.global.util.redis;

import com.sun.istack.NotNull;

public interface RedisTemplateService {
  public void deleteUserRefreshToken(@NotNull String userId);
  public String getUserRefreshToken(@NotNull String userId);
  public void setUserRefreshToken(@NotNull String userId, String refreshToken) ;
}