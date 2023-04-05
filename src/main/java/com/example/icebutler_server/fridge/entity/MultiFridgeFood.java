package com.example.icebutler_server.fridge.entity;

import com.example.icebutler_server.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
@Entity
public class MultiFridgeFood extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(nullable = false)
  private Long multiFridgeFoodIdx;
  private String fridgeFoodImgKey;
  private LocalDateTime shelfLife;
  private String foodComment;
  private String foodDetailName;

  /**
   * TODO: 이 부분도 마찬가지임. MultiFridgeUser를 할지 아님 아래 두 컬럼을 사용할지? -> MultiFridgeUser를 사용하는것으로 해결
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "userIdx")
  private User userIdx;

  @OneToOne
  @JoinColumn(name = "multiFridgeUserIdx")
  private MultiFridgeUser multiFridgeUser;

  @Builder
  public MultiFridgeFood(String fridgeFoodImgKey, LocalDateTime shelfLife, String foodComment, User userIdx, String foodDetailName, MultiFridgeUser multiFridgeUser) {
    this.fridgeFoodImgKey = fridgeFoodImgKey;
    this.shelfLife = shelfLife;
    this.foodComment = foodComment;
    this.foodDetailName = foodDetailName;
    this.userIdx = userIdx;
    this.multiFridgeUser = multiFridgeUser;
  }
}
