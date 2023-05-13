package com.example.icebutler_server.food.entity;

import com.example.icebutler_server.global.entity.BaseEntity;
import com.example.icebutler_server.global.entityListener.FoodEntityListener;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

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

    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "uuid-char")
    private UUID uuid;

    @Builder
    public Food(String foodName, String foodImgKey, FoodCategory foodCategory, UUID uuid) {
        this.foodName = foodName;
        this.foodImgKey = foodImgKey;
        this.foodCategory = foodCategory;
        this.uuid = uuid;
    }


}

