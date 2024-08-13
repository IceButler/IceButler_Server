package com.example.icebutler_server.fridge.dto.response;

import com.example.icebutler_server.global.util.FridgeUtils;
import com.example.icebutler_server.fridge.entity.FridgeFood;
import com.example.icebutler_server.global.util.AwsS3ImageUrlUtil;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@Schema(name = "FridgeFoodsRes", description = "냉장고 식품 정보")
public class FridgeFoodsRes {
    @Schema(description = "냉장고 식품 ID", example = "1")
    private Long fridgeFoodId;
    @Schema(description = "냉장고 식품 이름", example = "사과")
    private String foodName;
    @Schema(description = "냉장고 식품 이미지 URL", example = "https://~~/apple.jpg")
    private String foodImgUrl;
    @Schema(description = "남은 소비기간", example = "4")
    private int shelfLife;

    @QueryProjection
    public FridgeFoodsRes(Long fridgeFoodId, String foodName, String foodImgUrl, int shelfLife) {
        this.fridgeFoodId = fridgeFoodId;
        this.foodName = foodName;
        this.foodImgUrl = AwsS3ImageUrlUtil.toUrl(foodImgUrl);
        this.shelfLife = shelfLife;
    }

    public static FridgeFoodsRes toDto(FridgeFood fridgeFood) {
        return FridgeFoodsRes.builder()
                .fridgeFoodId(fridgeFood.getId())
                .foodName(fridgeFood.getFood().getFoodName())
                .foodImgUrl(AwsS3ImageUrlUtil.toUrl(fridgeFood.getFood().getFoodImgKey()))
                .shelfLife(FridgeUtils.calShelfLife(fridgeFood.getExpirationDate()))
                .build();
    }
}