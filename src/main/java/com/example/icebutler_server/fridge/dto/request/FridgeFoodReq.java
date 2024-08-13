package com.example.icebutler_server.fridge.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "FridgeFoodReq", description = "식품 정보")
public class FridgeFoodReq {
  @Schema(name = "foodName", description = "식품명")
  private String foodName;
  @Schema(name = "foodDetailName", description = "식품 상세명")
  private String foodDetailName;
  @Schema(name = "foodCategory", description = "식품 카테고리")
  private String foodCategory;
  @Schema(name = "expirationDate", description = "식품 소비기한")
  private String expirationDate;
  @Schema(name = "ownerId", description = "식품 소유자 ID")
  private Long ownerId;
  @Schema(name = "memo", description = "식품 메모")
  private String memo;
  @Schema(name = "imgKey", description = "식품 이미지 URL")
  private String imgKey;
}
