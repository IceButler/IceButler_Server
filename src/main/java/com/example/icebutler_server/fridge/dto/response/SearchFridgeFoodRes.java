package com.example.icebutler_server.fridge.dto.response;

import com.example.icebutler_server.fridge.entity.FridgeFood;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchFridgeFoodRes {
  private Long fridgeId;
  private Long userId;
  private List<SearchFoodRes> searchFoods;

  public static SearchFridgeFoodRes toDto(List<FridgeFood> searchFoods, Long fridgeId, Long userId) {
    SearchFridgeFoodRes searchFridgeFoodRes = new SearchFridgeFoodRes();
    searchFridgeFoodRes.searchFoods = searchFoods.stream().map(m -> SearchFoodRes.toDto(m.getId(), m.getFoodDetailName())).collect(Collectors.toList());
    searchFridgeFoodRes.fridgeId = fridgeId;
    searchFridgeFoodRes.userId = userId;
    return searchFridgeFoodRes;
  }
}
