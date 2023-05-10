package com.example.icebutler_server.food.entity;

import com.example.icebutler_server.food.exception.FoodCategoryNotFoundException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum FoodCategory{
  MEAT("육류"),
  FRUIT("과일"),
  VEGETABLE("채소"),
  BEVERAGE("음료"),
  AQUATIC_PRODUCTS("수산물"),
  BANCHAN("반찬"),
  SNACK("간식"),
  CONDIMENT("조미료"),
  PROCESSED_FOOD("가공식품"),
  ETC("기타");

  private final String name;


  private FoodCategory(String name) {

    this.name = name;
  }
  public String getImage() {
    return name().toLowerCase();
  }

  public static FoodCategory getFoodCategoryByName(String name){
    return Arrays.stream(FoodCategory.values())
            .filter(r -> r.getName().equals(name))
            .findAny().orElseThrow(FoodCategoryNotFoundException::new);
  }
}

