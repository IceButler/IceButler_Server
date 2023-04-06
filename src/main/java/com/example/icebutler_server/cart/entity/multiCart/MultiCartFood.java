package com.example.icebutler_server.cart.entity.multiCart;

import com.example.icebutler_server.global.entity.BaseEntity;
import com.example.icebutler_server.food.entity.Food;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
@Entity
public class MultiCartFood extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(nullable = false)
  private Long multiCartFoodIdx;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name="foodIdx")
  private Food food;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name="multiCartIdx")
  private MultiCart multiCart;

  @Builder
  public MultiCartFood(Food food, MultiCart multiCart) {
    this.food = food;
    this.multiCart = multiCart;
  }
}
