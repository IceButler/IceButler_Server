package com.example.icebutler_server.fridge.dto.fridge.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FridgeMainRes {
  List<FridgeFoodsRes> foodList;
}