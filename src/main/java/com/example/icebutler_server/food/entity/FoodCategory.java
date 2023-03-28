package com.example.icebutler_server.food.entity;

import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
@Entity
public class FoodCategory extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private int foodCategoryIdx;
    private String foodCategory;
    private boolean status;

    @OneToMany(mappedBy = "foodCategory",cascade = ALL)
    private List<Food> foods=new ArrayList<>();

}
