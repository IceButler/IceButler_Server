package com.example.icebutler_server.fridge.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "FridgeFoodsReq", description = "냉장고 식품 추가 요청 정보")
public class FridgeFoodsReq {
  @Schema(name = "fridgeFoods", description = "식품 정보")
  private List<FridgeFoodReq> fridgeFoods;
}
