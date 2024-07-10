package com.example.icebutler_server.food.dto.response;

import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.global.util.AwsS3ImageUrlUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(name = "FoodRes", description = "식품 검색 정보")
public class FoodRes {
    @Schema(name = "식품 ID")
    private Long foodId;
    @Schema(name = "식품명")
    private String foodName;
    @Schema(name = "식품 카테고리")
    private String foodCategory;
    @Schema(name = "식품 이미지 URL")
    private String foodImgUrl;

    public static FoodRes toDto(Food food) {
        return FoodRes.builder()
                .foodId(food.getId())
                .foodName(food.getFoodName())
                .foodCategory(food.getFoodCategory().getName())
                .foodImgUrl(AwsS3ImageUrlUtil.toUrl(food.getFoodImgKey()))
                .build();
    }
}
