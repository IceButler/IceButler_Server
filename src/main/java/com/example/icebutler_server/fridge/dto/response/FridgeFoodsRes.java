package com.example.icebutler_server.fridge.dto.response;
import com.example.icebutler_server.fridge.entity.Food;
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
