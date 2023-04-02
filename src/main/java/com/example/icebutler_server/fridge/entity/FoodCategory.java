package com.example.icebutler_server.fridge.entity;

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
    @OneToMany(mappedBy = "foodCategory",cascade = ALL)
    private List<Food> foods=new ArrayList<>();
}