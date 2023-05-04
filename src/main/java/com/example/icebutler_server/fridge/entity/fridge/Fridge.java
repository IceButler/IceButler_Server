package com.example.icebutler_server.fridge.entity.fridge;

import com.example.icebutler_server.cart.entity.cart.Cart;
import com.example.icebutler_server.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Fridge extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(nullable = false)
  private Long fridgeIdx;
  private String fridgeName;
  private String fridgeComment;

  @Builder
  public Fridge(
          String fridgeName,
          String fridgeComment) {
    this.fridgeName = fridgeName;
    this.fridgeComment = fridgeComment;
  }

  public void updateBasicFridgeInfo(String fridgeName, String fridgeComment) {
    this.fridgeName = fridgeName;
    this.fridgeComment = fridgeComment;
  }

  public void remove() {
    this.setIsEnable(false);
  }
}
