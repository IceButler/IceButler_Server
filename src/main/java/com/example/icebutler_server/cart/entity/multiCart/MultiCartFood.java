package com.example.icebutler_server.cart.entity.multiCart;

import com.example.icebutler_server.global.entity.BaseEntity;
import com.example.icebutler_server.food.entity.Food;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
@Entity
@SQLDelete(sql = "UPDATE multi_cart_food SET is_enable = false, update_at = current_timestamp WHERE multi_cart_food_id = ?")
public class MultiCartFood extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(nullable = false)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name="food_id")
  private Food food;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name="multi_cart_id")
  private MultiCart multiCart;

  @Builder
  public MultiCartFood(Food food, MultiCart multiCart) {
    this.food = food;
    this.multiCart = multiCart;
  }
}
