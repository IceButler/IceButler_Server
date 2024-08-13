package com.example.icebutler_server.fridge.dto.response;

import com.example.icebutler_server.fridge.entity.FridgeFood;
import com.example.icebutler_server.global.util.AwsS3ImageUrlUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "냉장고 식품 상세 정보", description = "FridgeFoodRes")
public class FridgeFoodRes {
  @Schema(description = "냉장고 ID", example = "1")
  private Long fridgeFoodId;
  @Schema(description = "식품 ID", example = "1")
  private Long foodId;
  @Schema(description = "식품명", example = "사과")
  private String foodName;
  @Schema(description = "식품 상세명", example = "무농약 사과")
  private String foodDetailName;
  @Schema(description = "식품 카테고리", example = "채소")
  private String foodCategory;
  @Schema(description = "식품 소비기한", example = "2024-01-01")
  private LocalDate expirationDate;
  @Schema(description = "남은 소비기간", example = "3")
  private int shelfLife;
  @Schema(description = "식품 소유자", example = "나야나")
  private String owner;
  @Schema(description = "식품 메모", example = "먹지마세요.")
  private String memo;
  @Schema(description = "식품 이미지 URL", example = "https://~~/apple.jpg")
  private String imgUrl;

  public static FridgeFoodRes toDto(FridgeFood fridgeFood) {
    return FridgeFoodRes.builder()
            .fridgeFoodId(fridgeFood.getId())
            .foodId(fridgeFood.getFood().getId())
            .foodName(fridgeFood.getFood().getFoodName())
            .foodDetailName(fridgeFood.getFoodDetailName())
            .foodCategory(fridgeFood.getFood().getFoodCategory().getName())
            .expirationDate(fridgeFood.getExpirationDate())
            .shelfLife(fridgeFood.getShelfLife())
            .owner(fridgeFood.getOwner() == null ? null : fridgeFood.getOwner().getNickname())
            .memo(fridgeFood.getMemo())
            .imgUrl(fridgeFood.getFridgeFoodImgKey() == null ? null : AwsS3ImageUrlUtil.toUrl(fridgeFood.getFridgeFoodImgKey()))
            .build();
  }
}