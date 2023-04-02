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
public class Food {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long foodIdx;
    private String foodName;
    private String foodIconName;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="foodCategoryIdx")
    private FoodCategory foodCategory;
    @OneToMany(mappedBy="food", cascade=ALL)
    private List<CartFood> cartFoods = new ArrayList<>();
    @OneToMany(mappedBy="food", cascade=ALL)
    private List<FridgeFood> fridgeFoods = new ArrayList<>();

    public void addCartFood(CartFood cartFood) {
        this.cartFoods.add(cartFood);
    }

    public void removeCartFood(CartFood cartFood) {
        this.cartFoods.removeIf((cf)->cf.getCardFoodIdx().equals(cartFood.getCardFoodIdx()));
    }
}