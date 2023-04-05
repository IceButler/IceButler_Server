package com.example.icebutler_server.fridge.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy="food", cascade=ALL)
    private List<CartFood> cartFoods = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy="food", cascade=ALL)
    private List<FridgeFood> fridgeFoods = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy="food", cascade=ALL)
    private List<MultiCartFood> multiCartFoods = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "food", cascade = ALL)
    private List<MultiFridgeFood> multiFridgeFoods = new ArrayList<>();

    public void addCartFood(CartFood cartFood) {
        this.cartFoods.add(cartFood);
    }

     public void removeCartFood(CartFood cartFood) {
        this.cartFoods.removeIf((cf)->cf.getCartFoodIdx().equals(cartFood.getCartFoodIdx()));
    }

    @Builder
    public Food(String foodName, String foodIconName, FoodCategory foodCategory) {
        this.foodName = foodName;
        this.foodIconName = foodIconName;
        this.foodCategory = foodCategory;
    }

}

