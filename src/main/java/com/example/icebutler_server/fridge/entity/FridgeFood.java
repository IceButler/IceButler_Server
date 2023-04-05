package com.example.icebutler_server.fridge.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;
import com.example.icebutler_server.user.entity.User;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class FridgeFood extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(nullable = false)
  private Long fridgeFoodIdx;
  private LocalDateTime shelfLife;
  private String fridgeFoodImgKey;
  private String foodComment;
  private String foodDetailName;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "userIdx")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "foodIdx")
  private Food food;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "fridgeIdx")
  private Fridge fridge;
}
