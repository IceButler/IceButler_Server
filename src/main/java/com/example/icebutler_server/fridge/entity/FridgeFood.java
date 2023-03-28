package com.example.icebutler_server.fridge.entity;

import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.global.entity.BaseEntity;
import com.example.icebutler_server.user.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import static javax.persistence.CascadeType.ALL;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
@Entity
public class FridgeFood extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private int fridgeFoodIdx;
    private int foodIdx;
    private int userIdx;
    private LocalDateTime shelfLife;
    private TextComponent memo;
    private boolean status;

    @OneToMany(mappedBy="fridgeFood", cascade=ALL)
    private List<Food> foods=new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="userIdx")
    private User user;

    @OneToMany(mappedBy="fridgeFood", cascade=ALL)
    private List<FridgeFoodImg> fridgeFoodImgs=new ArrayList<>();










}
