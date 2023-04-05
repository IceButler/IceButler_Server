package com.example.icebutler_server.fridge.entity;

import com.example.icebutler_server.global.entity.BaseEntity;
import com.example.icebutler_server.user.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
@Entity
public class MultiFridgeFood extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(nullable = false)
  private int multiFridgeFoodIdx;
  private String fridgeFoodImg;
  private LocalDateTime shelfLife;
  private TextComponent memo;

  /**
   * TODO: 이 부분도 마찬가지임. MultiFridgeUser를 할지 아님 아래 두 컬럼을 사용할지?
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "userIdx")
  private User userIdx;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "foodIdx")
  private Food food;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "multiFridgeIdx")
  private MultiFridge multiFridge;

  public MultiFridgeFood(String fridgeFoodImg, LocalDateTime shelfLife, TextComponent memo, User userIdx, Food food, MultiFridge multiFridge) {
    this.fridgeFoodImg = fridgeFoodImg;
    this.shelfLife = shelfLife;
    this.memo = memo;
    this.userIdx = userIdx;
    this.food = food;
    this.multiFridge = multiFridge;
  }
}
