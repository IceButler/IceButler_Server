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
public class Cart extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long cartIdx;

    @OneToMany(fetch = FetchType.LAZY, mappedBy="cart", cascade = ALL)
    private List<CartFood> cartFoods = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "cart", cascade = ALL)
    private Fridge fridge;

    public void addCartFood(CartFood cartFood) {
        this.cartFoods.add(cartFood);
    }

    public void removeCartFood(CartFood cartFood) {
        this.cartFoods.removeIf((cf) -> cf.getCartFoodIdx().equals(cartFood.getCartFoodIdx()));
    }
}
