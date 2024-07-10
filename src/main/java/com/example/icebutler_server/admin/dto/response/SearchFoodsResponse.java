package com.example.icebutler_server.admin.dto.response;

import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.global.util.AwsS3ImageUrlUtil;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchFoodsResponse {
  private Long foodId;
  private String foodCategory;
  private String foodName;
  private String foodImgUrl;

  public static SearchFoodsResponse toDto(Food food) {
    SearchFoodsResponse searchFoodsResponse = new SearchFoodsResponse();
    searchFoodsResponse.foodId = food.getId();
    searchFoodsResponse.foodCategory = food.getFoodCategory().getName();
    searchFoodsResponse.foodName = food.getFoodName();
    searchFoodsResponse.foodImgUrl = AwsS3ImageUrlUtil.toUrl(food.getFoodImgKey());
    return searchFoodsResponse;
  }

  @Builder
  public SearchFoodsResponse(Long foodId, String foodCategory, String foodName, String foodImgUrl) {
    this.foodId = foodId;
    this.foodCategory = foodCategory;
    this.foodName = foodName;
    this.foodImgUrl = foodImgUrl;
  }
}
