package com.example.icebutler_server.fridge.dto.fridge.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchFoodRes {
  private Long fridgeFoodIdx;
  private String foodDetailName;

  public static SearchFoodRes toDto(Long fridgeFoodIdx, String foodDetailName) {
    SearchFoodRes searchFoodRes = new SearchFoodRes();
    searchFoodRes.fridgeFoodIdx = fridgeFoodIdx;
    searchFoodRes.foodDetailName = foodDetailName;
    return searchFoodRes;
  }
}
