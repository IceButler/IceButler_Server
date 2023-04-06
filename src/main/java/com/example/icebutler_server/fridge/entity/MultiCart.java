package com.example.icebutler_server.fridge.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import static javax.persistence.CascadeType.ALL;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
@Entity
public class MultiCart extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(nullable = false)
  private int multiCartIdx;

  @OneToOne
  @JoinColumn(name = "multiFridgeUserIdx")
  private MultiFridgeUser multiFridgeUser;

  @OneToMany(fetch = FetchType.LAZY, mappedBy="multiCart", cascade=ALL)
  private List<MultiCartFood> multiCartFoods = new ArrayList<>();

  public void addCartFood(MultiCartFood multiCartFood) {
    this.multiCartFoods.add(multiCartFood);
  }
}
