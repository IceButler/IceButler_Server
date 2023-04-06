package com.example.icebutler_server.fridge.entity;

import com.example.icebutler_server.global.entity.BaseEntity;
import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
@Entity
public class MultiFridgeFood extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(nullable = false)
  private Long multiFridgeFoodIdx;
  private String fridgeFoodImgKey;
  private LocalDate shelfLife;
  private String memo;
  private String foodDetailName;
  private FoodDeleteStatus foodDeleteStatus;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "foodIdx")
  private Food food;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "userIdx")
  private User owner;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "multiFridgeIdx")
  private com.example.icebutler_server.fridge.entity.MultiFridge multiFridge;

  @Builder
  public MultiFridgeFood(String fridgeFoodImgKey, LocalDate shelfLife, String memo, Food food, String foodDetailName, MultiFridge multiFridge, User owner) {
    this.fridgeFoodImgKey = fridgeFoodImgKey;
    this.shelfLife = shelfLife;
    this.memo = memo;
    this.foodDetailName = foodDetailName;
    this.food = food;
    this.owner = owner;
    this.multiFridge = multiFridge;
  }
}
