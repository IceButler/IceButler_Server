package com.example.icebutler_server.recipe.entity;

import com.example.icebutler_server.fridge.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
@Entity
public class Cookery extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(nullable = false)
  private Long cookeryIdx;
  private String cookeryImgKey;
  private String description;
  private int nextIdx;

  @ManyToOne
  @JoinColumn(name = "recipeIdx")
  private Recipe recipe;
}
