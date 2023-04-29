package com.example.icebutler_server.cart.entity.multiCart;

import com.example.icebutler_server.global.entity.BaseEntity;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridgeUser;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
@Entity
@SQLDelete(sql = "UPDATE multi_cart SET is_enable = false, last_modified_date = current_timestamp WHERE multi_cart_idx = ?")
public class MultiCart extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(nullable = false)
  private Long multiCartIdx;

  @OneToOne
  @JoinColumn(name = "multiFridgeUserIdx", nullable = false)
  private MultiFridgeUser multiFridgeUser;

}
