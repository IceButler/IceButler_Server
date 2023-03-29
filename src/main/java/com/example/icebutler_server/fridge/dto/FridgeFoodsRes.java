package com.example.icebutler_server.fridge.dto;

import com.example.icebutler_server.food.entity.Food;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FridgeFoodsRes {
  private List<Food> ownerFoods;
  private List<List<Food>> fridgeUserFoods;
}
