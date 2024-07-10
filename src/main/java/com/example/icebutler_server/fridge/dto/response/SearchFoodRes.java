package com.example.icebutler_server.fridge.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchFoodRes {
  private Long fridgeFoodId;
  private String foodDetailName;

  public static SearchFoodRes toDto(Long fridgeFoodId, String foodDetailName) {
    SearchFoodRes searchFoodRes = new SearchFoodRes();
    searchFoodRes.fridgeFoodId = fridgeFoodId;
    searchFoodRes.foodDetailName = foodDetailName;
    return searchFoodRes;
  }
}
