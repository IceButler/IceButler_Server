package com.example.icebutler_server.recipe.entity;

import lombok.Getter;

@Getter
public enum RecipeCategory {
  SNACK("간식"),
  SOUP("국/찌개/전골"),
  RICE("밥/죽/면"),
  BANCHAN("반찬"),
  DESSERT("디저트"),
  SALAD("샐러드"),
  BEVERAGE("차/음료/술"),
  ETC("기타");

  private final String category;

  private RecipeCategory(String category) {
    this.category = category;
  }
}
