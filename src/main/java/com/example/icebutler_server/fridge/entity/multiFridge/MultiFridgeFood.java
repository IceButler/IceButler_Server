package com.example.icebutler_server.fridge.entity.multiFridge;

import com.example.icebutler_server.global.entity.BaseEntity;
import com.example.icebutler_server.food.entity.Food;
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

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "foodIdx")
  private Food food;

  @OneToOne
  @JoinColumn(name = "multiFridgeUserIdx")
  private MultiFridgeUser multiFridgeUser;

  @Builder
  public MultiFridgeFood(String fridgeFoodImgKey, LocalDate shelfLife, String memo, Food food, String foodDetailName, MultiFridgeUser multiFridgeUser) {
    this.fridgeFoodImgKey = fridgeFoodImgKey;
    this.shelfLife = shelfLife;
    this.memo = memo;
    this.foodDetailName = foodDetailName;
    this.food = food;
    this.multiFridgeUser = multiFridgeUser;
  }
}
