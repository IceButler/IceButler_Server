package com.example.icebutler_server.user.entity;

import com.example.icebutler_server.cart.entity.Cart;
import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.fridge.entity.Fridge;
import com.example.icebutler_server.fridge.entity.FridgeFood;
import com.example.icebutler_server.fridge.entity.FridgeUser;
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
public class User extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long userIdx;
    private String email;
    private String nickname;
    private String provider;
    private String profileImage;
    private Boolean loginStatus;

    @OneToMany(mappedBy="owner", cascade=ALL)
    private List<FridgeUser> fridgeUsers = new ArrayList<>();

    @OneToMany(mappedBy="owner", cascade=ALL)
    private List<Fridge> fridges = new ArrayList<>();

    @OneToMany(mappedBy="owner", cascade=ALL)
    private List<Food> foods = new ArrayList<>();

    @OneToMany(mappedBy="owner", cascade=ALL)
    private List<FridgeFood> fridgeFoods=new ArrayList<>();


    @OneToOne(mappedBy = "owner",fetch = FetchType.LAZY)
    private Cart cart;





}
