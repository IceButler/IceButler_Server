package com.example.icebutler_server.cart.entity.multiCart;

import com.example.icebutler_server.global.entity.BaseEntity;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridgeUser;
import com.example.icebutler_server.global.entityListener.MultiCartEntityListener;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
@Entity
@SQLDelete(sql = "UPDATE multi_cart SET is_enable = false, update_at = current_timestamp WHERE multi_cart_idx = ?")
@EntityListeners(MultiCartEntityListener.class)
public class MultiCart extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(nullable = false)
  private Long multiCartIdx;

  @OneToOne
  @JoinColumn(name = "multiFridgeUserIdx", nullable = false)
  private MultiFridgeUser multiFridgeUser;

  @Builder
  public MultiCart(MultiFridgeUser multiFridgeUser) {
    this.multiFridgeUser = multiFridgeUser;
  }
}
