package com.example.icebutler_server.fridge.entity;

import com.example.icebutler_server.user.entity.User;
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
    private int foodIdx;
    private String foodName;
    private String foodIconName;
    private String foodCategory;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cartIdx")
    private Cart cart;
    @OneToMany(mappedBy="food", cascade=ALL)
    private List<CartFood> cartFoods = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="fridgeFoodIdx")
    private FridgeFood fridgeFood;

    public void addCartFood(CartFood cartFood) {
        this.cartFoods.add(cartFood);
    }
}
