package com.example.icebutler_server.food.entity;

import com.example.icebutler_server.global.entity.BaseEntity;
import com.example.icebutler_server.global.entityListener.FoodEntityListener;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
@Entity
@SQLDelete(sql = "UPDATE food SET is_enable = false, update_at = current_timestamp WHERE food_idx = ?")
@EntityListeners(FoodEntityListener.class)
public class Food extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long foodIdx;
    private String foodName;
    private String foodImgKey;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FoodCategory foodCategory;

    @Builder
    public Food(String foodName, String foodImgKey, FoodCategory foodCategory) {
        this.foodName = foodName;
        this.foodImgKey = foodImgKey;
        this.foodCategory = foodCategory;
    }

}

