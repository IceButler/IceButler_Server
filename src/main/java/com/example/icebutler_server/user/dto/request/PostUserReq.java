package com.example.icebutler_server.user.dto.request;

import com.example.icebutler_server.user.entity.Provider;
import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
public class PostUserReq {
  private String provider;
  private String email;
}
