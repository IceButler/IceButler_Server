package com.example.icebutler_server.admin.dto.response;

import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.food.entity.FoodCategory;
import com.example.icebutler_server.global.util.AwsS3ImageUrlUtil;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchFoodsResponse {
  private Long foodIdx;
  private String foodCategory;
  private String foodName;
  private String foodImgUrl;

  public static SearchFoodsResponse toDto(Food food) {
    SearchFoodsResponse searchFoodsResponse = new SearchFoodsResponse();
    searchFoodsResponse.foodIdx = food.getFoodIdx();
    searchFoodsResponse.foodCategory = food.getFoodCategory().getName();
    searchFoodsResponse.foodName = food.getFoodName();
    searchFoodsResponse.foodImgUrl = AwsS3ImageUrlUtil.toUrl(food.getFoodImgKey());
    return searchFoodsResponse;
  }

  @Builder
  public SearchFoodsResponse(Long foodIdx, String foodCategory, String foodName, String foodImgUrl) {
    this.foodIdx = foodIdx;
    this.foodCategory = foodCategory;
    this.foodName = foodName;
    this.foodImgUrl = foodImgUrl;
  }
}
