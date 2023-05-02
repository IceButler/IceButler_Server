package com.example.icebutler_server.fridge.entity.fridge;

import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.food.entity.FoodDeleteStatus;
import com.example.icebutler_server.global.entity.BaseEntity;
import com.example.icebutler_server.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class FridgeFood extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(nullable = false)
  private Long fridgeFoodIdx;
  private LocalDate shelfLife;
  private String fridgeFoodImgKey;
  private String memo;
  private String foodDetailName;

  @Enumerated(EnumType.STRING)
  private FoodDeleteStatus foodDeleteStatus;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "foodIdx")
  private Food food;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "userIdx")
  private User owner;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "fridgeIdx")
  private Fridge fridge;

  @Builder
  public FridgeFood(User owner, Food food, Fridge fridge, String foodDetailName, LocalDate shelfLife, String memo, String fridgeFoodImgKey) {
    this.shelfLife = shelfLife;
    this.fridgeFoodImgKey = fridgeFoodImgKey;
    this.memo = memo;
    this.foodDetailName = foodDetailName;
    this.owner = owner;
    this.food = food;
    this.fridge = fridge;
    this.foodDeleteStatus = null;
  }

  public void updateFridgeFoodInfo(Food food) {
    this.food = food;
  }

  public void updateFridgeFoodInfo(String foodDetailName, String memo, LocalDate shelfLife, String imgUrl) {
    this.foodDetailName = foodDetailName;
    this.memo = memo;
    this.shelfLife = shelfLife;
    this.fridgeFoodImgKey = imgUrl;
  }

  public void updateMultiFridgeFoodOwner(User newOwner) {
    this.owner = newOwner;
  }

  public void remove() {
    this.setIsEnable(false);
  }

  public void removeWithStatus(FoodDeleteStatus deleteStatus) {
    this.setIsEnable(false);
    this.foodDeleteStatus = deleteStatus;
  }
}
