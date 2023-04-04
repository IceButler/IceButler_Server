package com.example.icebutler_server.fridge.entity;

import com.example.icebutler_server.global.entity.BaseEntity;
import com.example.icebutler_server.user.entity.User;
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
  private Long multiCartIdx;

  @OneToMany(mappedBy="multiCart", cascade=ALL)
  private List<MultiCartFood> multiCartFoods = new ArrayList<>();

  @OneToMany(mappedBy = "multiCart", cascade = ALL)
  private List<MultiFridge> multiFridges = new ArrayList<>();

  @OneToOne(cascade = ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "userIdx")
  private User owner;

  @OneToOne
  private MultiFridge multiFridge;

  private String cartStatus;

  public void addCartFood(MultiCartFood multiCartFood) {
    this.multiCartFoods.add(multiCartFood);
  }
}
