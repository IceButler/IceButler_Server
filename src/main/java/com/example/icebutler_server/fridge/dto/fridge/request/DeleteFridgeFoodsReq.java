package com.example.icebutler_server.fridge.dto.fridge.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "DeleteFridgeFoodsReq", description = "냉장고 식품 삭제 요청 정보")
public class DeleteFridgeFoodsReq {
  @Schema(name = "deleteFoods", description = "식품 ID")
  private List<Long> deleteFoods;
}
