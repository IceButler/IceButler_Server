package com.example.icebutler_server.cart.entity.multiCart;

import com.example.icebutler_server.global.entity.BaseEntity;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridgeUser;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
@Entity
public class MultiCart extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(nullable = false)
  private Long multiCartIdx;

  @OneToOne
  @JoinColumn(name = "multiFridgeUserIdx")
  private MultiFridgeUser multiFridgeUser;

}
