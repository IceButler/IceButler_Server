package com.example.icebutler_server.admin.dto.request;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
public class RemoveFoodsRequest {
  private List<RemoveFoodRequest> removeFoods;
}
