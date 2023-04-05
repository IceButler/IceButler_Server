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
  private int multiCartIdx;

  /**
   * TODO: 사용자 및 냉장고의 정보가 들어가야 하므로 MultiFridgeUser와 연결할지 아니면 userIdx, multiFridgeIdx 두개로 나눈 컬럼으로 할지 결정
   */
  @OneToOne
  @JoinColumn(name = "multiFridgeUserIdx")
  private MultiFridgeUser multiFridgeUser;

//  @ManyToOne(fetch = FetchType.LAZY)
//  @JoinColumn(name = "userIdx")
//  private User user;
//
//  @ManyToOne(fetch = FetchType.LAZY)
//  @JoinColumn(name = "multiFridgeIdx")
//  private MultiFridge multiFridge;

  @OneToMany(fetch = FetchType.LAZY, mappedBy="multiCart", cascade=ALL)
  private List<MultiCartFood> multiCartFoods = new ArrayList<>();

  public void addCartFood(MultiCartFood multiCartFood) {
    this.multiCartFoods.add(multiCartFood);
  }
}
