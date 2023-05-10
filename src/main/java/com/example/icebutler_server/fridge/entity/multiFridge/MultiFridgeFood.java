package com.example.icebutler_server.fridge.entity.multiFridge;

import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.food.entity.FoodDeleteStatus;
import com.example.icebutler_server.global.entity.BaseEntity;
import com.example.icebutler_server.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
@Entity
@SQLDelete(sql = "UPDATE multi_fridge_food SET is_enable = false, update_at = current_timestamp WHERE multi_fridge_food_idx = ?")
public class MultiFridgeFood extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(nullable = false)
  private Long multiFridgeFoodIdx;

  private String fridgeFoodImgKey;

  @Column(nullable = false)
  private LocalDate shelfLife;

  private String memo;

  @Column(nullable = false)
  private String foodDetailName;

  @Enumerated(EnumType.STRING)
  private FoodDeleteStatus foodDeleteStatus;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "foodIdx")
  private Food food;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "userIdx")
  private User owner;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "multiFridgeIdx")
  private MultiFridge multiFridge;

  public void updateBasicMultiFridgeFoodInfo(String foodDetailName, String memo, LocalDate shelfLife, String imgUrl) {
    this.foodDetailName = foodDetailName;
    this.memo = memo;
    this.shelfLife = shelfLife;
    this.fridgeFoodImgKey = imgUrl;
  }

  public void toUpdateMultiFridgeFoodInfo(Food food) {
    this.food = food;
  }

  public void toUpdateMultiFridgeFoodOwner(User newOwner) {
    this.owner = newOwner;
  }
  @Builder
  public MultiFridgeFood(String fridgeFoodImgKey, LocalDate shelfLife, String memo, String foodDetailName, FoodDeleteStatus foodDeleteStatus, Food food, User owner, MultiFridge multiFridge) {
    this.fridgeFoodImgKey = fridgeFoodImgKey;
    this.shelfLife = shelfLife;
    this.memo = memo;
    this.foodDetailName = foodDetailName;
    this.foodDeleteStatus = foodDeleteStatus;
    this.food = food;
    this.owner = owner;
    this.multiFridge = multiFridge;
  }

    public void removeWithStatus(FoodDeleteStatus deleteStatus) {
      this.setIsEnable(false);
      this.foodDeleteStatus = deleteStatus;
    }
}
