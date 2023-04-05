package com.example.icebutler_server.fridge.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static javax.persistence.CascadeType.ALL;
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
  private String description;
  private String fridgeFoodImg;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "userIdx")
  private User useridx;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "foodIdx")
  private Food food;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "fridgeIdx")
  private Fridge fridge;

//  @OneToMany(fetch = FetchType.LAZY, mappedBy = "fridgeFood", cascade = ALL)
//  private List<FridgeFoodImg> fridgeFoodImgs = new ArrayList<>();

}
