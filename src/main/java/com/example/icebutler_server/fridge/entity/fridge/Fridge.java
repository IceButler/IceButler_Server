package com.example.icebutler_server.fridge.entity.fridge;

import com.example.icebutler_server.global.entity.BaseEntity;
import org.hibernate.annotations.SQLDelete;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@SQLDelete(sql = "UPDATE fridge SET is_enable = false, last_modified_date = current_timestamp WHERE fridge_idx = ?")
public class Fridge extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long fridgeIdx;

    @Column(nullable = false)
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
