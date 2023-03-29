package com.example.icebutler_server.food.entity;

import com.example.icebutler_server.cart.entity.Cart;
import com.example.icebutler_server.fridge.entity.FridgeFood;
import com.example.icebutler_server.global.entity.BaseEntity;
import com.example.icebutler_server.user.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
@Entity
public class Food extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private int foodIdx;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="foodCategoryIdx")
//    private FoodCategory foodCategory;

    private String foodName;
    private String foodIconName;
    private String foodCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userIdx")
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cartIdx")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="fridgeFoodIdx")
    private FridgeFood fridgeFood;



}
