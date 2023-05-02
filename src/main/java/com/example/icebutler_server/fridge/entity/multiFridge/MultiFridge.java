package com.example.icebutler_server.fridge.entity.multiFridge;

import com.example.icebutler_server.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
@Entity
public class MultiFridge extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(nullable = false)
  private Long multiFridgeIdx;

  private String fridgeName;
  private String fridgeComment;

  @Builder
  public MultiFridge(String fridgeName, String fridgeComment) {
    this.fridgeName = fridgeName;
    this.fridgeComment = fridgeComment;
  }

    public void updateBasicFridgeInfo(String fridgeName, String fridgeComment) {
        this.fridgeName = fridgeName;
        this.fridgeComment = fridgeComment;
    }
}
