package com.example.icebutler_server.fridge.dto.response;

import com.example.icebutler_server.fridge.dto.assembler.FridgeUtils;
import com.example.icebutler_server.fridge.entity.FridgeFood;
import com.example.icebutler_server.global.util.AwsS3ImageUrlUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "FridgeFoodRes", description = "냉장고 식품 상세 정보")
public class FridgeFoodRes {
  @Schema(name = "fridgeFoodIdx", description = "냉장고 ID")
  private Long fridgeFoodIdx;
  @Schema(name = "foodIdx", description = "식품 ID")
  private Long foodIdx;
  @Schema(name = "foodName", description = "식품명")
  private String foodName;
  @Schema(name = "foodDetailName", description = "식품 상세명")
  private String foodDetailName;
  @Schema(name = "foodCategory", description = "식품 카테고리")
  private String foodCategory;
  @Schema(name = "shelfLife", description = "식품 소비기한")
  private String shelfLife;
  @Schema(name = "day", description = "식품 소비기한 디데이")
  private int day;
  @Schema(name = "owner", description = "식품 소유자")
  private String owner;
  @Schema(name = "memo", description = "식품 메모")
  private String memo;
  @Schema(name = "imgUrl", description = "식품 이미지 URL")
  private String imgUrl;

  public static FridgeFoodRes toDto(FridgeFood fridgeFood) {
    return FridgeFoodRes.builder()
            .fridgeFoodIdx(fridgeFood.getId())
            .foodIdx(fridgeFood.getFood().getId())
            .foodName(fridgeFood.getFood().getFoodName())
            .foodDetailName(fridgeFood.getFoodDetailName())
            .foodCategory(fridgeFood.getFood().getFoodCategory().getName())
            .shelfLife(fridgeFood.getShelfLife().format(DateTimeFormatter.ISO_DATE))
            .day(FridgeUtils.calShelfLife(fridgeFood.getShelfLife()))
            .owner(fridgeFood.getOwner() == null ? null : fridgeFood.getOwner().getNickname())
            .memo(fridgeFood.getMemo())
            .imgUrl(fridgeFood.getFridgeFoodImgKey() == null ? null : AwsS3ImageUrlUtil.toUrl(fridgeFood.getFridgeFoodImgKey()))
            .build();
  }
}