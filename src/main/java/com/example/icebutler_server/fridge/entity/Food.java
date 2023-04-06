package com.example.icebutler_server.fridge.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
@Entity
public class Food {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long foodIdx;
    private String foodName;
    private String foodIconName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FoodCategory foodCategory;

    @Builder
    public Food(String foodName, String foodIconName, FoodCategory foodCategory) {
        this.foodName = foodName;
        this.foodIconName = foodIconName;
        this.foodCategory = foodCategory;
    }

}

