package com.example.icebutler_server.admin.dto.request;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class RemoveFoodRequest {
  private Long foodIdx;
}
