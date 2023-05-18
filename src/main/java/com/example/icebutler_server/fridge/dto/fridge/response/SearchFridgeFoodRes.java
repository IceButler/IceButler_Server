package com.example.icebutler_server.fridge.dto.fridge.response;

import com.example.icebutler_server.fridge.entity.fridge.FridgeFood;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridgeFood;
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
  private Long fridgeIdx;
  private Long userIdx;
  private List<SearchFoodRes> searchFoods;

  public static SearchFridgeFoodRes toDto(List<FridgeFood> searchFoods, Long fridgeIdx, Long userIdx) {
    SearchFridgeFoodRes searchFridgeFoodRes = new SearchFridgeFoodRes();
    searchFridgeFoodRes.searchFoods = searchFoods.stream().map(m -> SearchFoodRes.toDto(m.getFridgeFoodIdx(), m.getFoodDetailName())).collect(Collectors.toList());
    searchFridgeFoodRes.fridgeIdx = fridgeIdx;
    searchFridgeFoodRes.userIdx = userIdx;
    return searchFridgeFoodRes;
  }

  public static SearchFridgeFoodRes toMultiDto(List<MultiFridgeFood> searchFoods, Long fridgeIdx, Long userIdx) {
    SearchFridgeFoodRes searchFridgeFoodRes = new SearchFridgeFoodRes();
    searchFridgeFoodRes.searchFoods = searchFoods.stream().map(m -> SearchFoodRes.toDto(m.getMultiFridgeFoodIdx(), m.getFoodDetailName())).collect(Collectors.toList());
    searchFridgeFoodRes.fridgeIdx = fridgeIdx;
    searchFridgeFoodRes.userIdx = userIdx;
    return searchFridgeFoodRes;
  }
}
