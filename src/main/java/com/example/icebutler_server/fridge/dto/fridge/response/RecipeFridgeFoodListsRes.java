package com.example.icebutler_server.fridge.dto.fridge.response;

import com.example.icebutler_server.food.entity.Food;
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
public class RecipeFridgeFoodListsRes {
  private List<RecipeFridgeFoodListRes> foodList;

  public static RecipeFridgeFoodListsRes toDto(List<Food> food){
    return new RecipeFridgeFoodListsRes(food.stream()
            .map(f -> new RecipeFridgeFoodListRes(f.getFoodIdx()))
            .collect(Collectors.toList()));
  }

}