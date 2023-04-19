package com.example.icebutler_server.fridge.dto.fridge.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FridgeFoodsReq {
  private List<FridgeFoodReq> fridgeFoods;
}
