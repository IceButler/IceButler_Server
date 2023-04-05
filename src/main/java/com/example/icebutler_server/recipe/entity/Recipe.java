package com.example.icebutler_server.recipe.entity;

import com.example.icebutler_server.fridge.entity.BaseEntity;
import com.example.icebutler_server.user.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
@Entity
public class Recipe extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(nullable = false)
  private Long recipeIdx;
  private String recipeName;
  private String recipeImgKey;
  private int quantity;
  private Date leadTime;

  @ManyToOne
  @JoinColumn(name = "userIdx")
  private User user;

  @OneToMany(mappedBy="recipe", cascade=ALL)
  private List<Cookery> cookeries = new ArrayList<>();

  @OneToMany(mappedBy="recipe", cascade=ALL)
  private List<RecipeIngredient> recipeIngredients = new ArrayList<>();

  @OneToMany(mappedBy="recipe", cascade=ALL)
  private List<RecipeLike> recipeLikes = new ArrayList<>();

  @OneToMany(mappedBy="recipe", cascade=ALL)
  private List<RecipeReport> recipeReports = new ArrayList<>();

}
