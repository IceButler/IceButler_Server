package com.example.icebutler_server.food.entity;

import com.example.icebutler_server.food.exception.FoodCategoryNotFoundException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum FoodCategory{
  MEAT("육류", "meat.png"),
  FRUIT("과일", "fruit.png"),
  VEGETABLE("채소", "vegetable.png"),
  BEVERAGE("음료", "beverage.png"),
  AQUATIC_PRODUCTS("수산물", "aquatic_products.png"),
  BANCHAN("반찬", "banchan.png"),
  SNACK("간식", "snack.png"),
  CONDIMENT("조미료", "condiment.png"),
  PROCESSED_FOOD("가공식품", "processed_food.png"),
  ETC("기타", "etc.png");

  private final String name;
  private final String foodCategoryImgUrl;

  private FoodCategory(String name,String foodCategoryImgUrl) {
    this.foodCategoryImgUrl=foodCategoryImgUrl;
    this.name = name;
  }

  public static FoodCategory getFoodCategoryByName(String name){
    return Arrays.stream(FoodCategory.values())
            .filter(r -> r.getName().equals(name))
            .findAny().orElseThrow(FoodCategoryNotFoundException::new);
  }
}

