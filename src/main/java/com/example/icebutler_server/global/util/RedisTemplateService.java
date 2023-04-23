package com.example.icebutler_server.global.util;

import com.sun.istack.NotNull;

public interface RedisTemplateService {
  public void deleteUserRefreshToken(@NotNull Long userIdx);
  public String getUserRefreshToken(@NotNull Long userIdx);
  public void setUserRefreshToken(@NotNull Long userIdx, String refreshToken) ;

  }
