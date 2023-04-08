package com.example.icebutler_server.fridge.entity.multiFridge;

import com.example.icebutler_server.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

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

  @Where(clause = "is_enable = 1")
  @OneToMany(fetch = FetchType.LAZY, mappedBy="multiFridge", cascade=ALL)
  private List<MultiFridgeUser> multiFridgeUsers = new ArrayList<>();

  @Where(clause = "is_enable = 1")
  @OneToMany(fetch = FetchType.LAZY, mappedBy="multiFridge", cascade=ALL)
  private List<MultiFridgeFood> multiFridgeFoods = new ArrayList<>();

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
