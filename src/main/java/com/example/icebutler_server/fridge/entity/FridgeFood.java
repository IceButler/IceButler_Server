package com.example.icebutler_server.fridge.entity;

import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.food.entity.FoodDeleteStatus;
import com.example.icebutler_server.fridge.dto.request.FridgeFoodReq;
import com.example.icebutler_server.global.entity.BaseEntity;
import com.example.icebutler_server.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@SQLDelete(sql = "UPDATE fridge_food SET is_enable = false, updated_at = current_timestamp WHERE id = ?")
public class FridgeFood extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private LocalDate shelfLife;

    private String fridgeFoodImgKey;

    private String memo;

    @Column(nullable = false)
    private String foodDetailName;

    @Enumerated(EnumType.STRING)
    private FoodDeleteStatus foodDeleteStatus;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "food_id")
    private Food food;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fridge_id")
    private Fridge fridge;

    @Builder
    public FridgeFood(User owner, Food food, Fridge fridge, String foodDetailName, LocalDate shelfLife, String memo, String fridgeFoodImgKey) {
        this.shelfLife = shelfLife;
        this.fridgeFoodImgKey = fridgeFoodImgKey;
        this.memo = memo;
        this.foodDetailName = foodDetailName;
        this.owner = owner;
        this.food = food;
        this.fridge = fridge;
        this.foodDeleteStatus = null;
    }

    public void updateFridgeFoodInfo(Food food) {
        this.food = food;
    }

    public void updateFridgeFoodInfo(String foodDetailName, String memo, LocalDate shelfLife, String imgUrl) {
        this.foodDetailName = foodDetailName;
        this.memo = memo;
        this.shelfLife = shelfLife;
        this.fridgeFoodImgKey = imgUrl;
    }

    public void updateFridgeFoodOwner(User newOwner) {
        this.owner = newOwner;
    }

    public void remove() {
        this.setIsEnable(false);
    }

    public void removeWithStatus(FoodDeleteStatus deleteStatus) {
        this.setIsEnable(false);
        this.foodDeleteStatus = deleteStatus;
    }

    public static FridgeFood toEntity(User owner, Fridge fridge, Food food, FridgeFoodReq fridgeFoodReq) {
        return FridgeFood.builder()
                .fridge(fridge)
                .food(food)
                .foodDetailName(fridgeFoodReq.getFoodDetailName())
                .shelfLife(LocalDate.parse(fridgeFoodReq.getShelfLife()))
                .owner(owner)
                .memo(fridgeFoodReq.getMemo())
                .fridgeFoodImgKey(fridgeFoodReq.getImgKey())
                .build();
    }
}
