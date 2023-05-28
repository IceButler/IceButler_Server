package com.example.icebutler_server.global.util.redis;

import com.sun.istack.NotNull;

public interface RedisTemplateService {
  public void deleteUserRefreshToken(@NotNull String userIdx);
  public String getUserRefreshToken(@NotNull String userIdx);
  public void setUserRefreshToken(@NotNull String userIdx, String refreshToken) ;
}