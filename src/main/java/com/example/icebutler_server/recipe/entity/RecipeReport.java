package com.example.icebutler_server.recipe.entity;

import com.example.icebutler_server.global.entity.BaseEntity;
import com.example.icebutler_server.user.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class RecipeReport extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(nullable = false)
  private Long recipeReportIdx;

  @ManyToOne
  @JoinColumn(name = "recipeIdx")
  private Recipe recipe;

  @ManyToOne
  @JoinColumn(name = "userIdx")
  private User user;

  private String reason;
}
