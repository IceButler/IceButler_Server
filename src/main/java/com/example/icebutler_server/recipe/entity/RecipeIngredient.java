package com.example.icebutler_server.recipe.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class RecipeIngredient {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(nullable = false)
  private Long recipeIngredientIdx;
  private String ingredient;

  @ManyToOne
  @JoinColumn(name = "recipeIdx")
  private Recipe recipe;
}
