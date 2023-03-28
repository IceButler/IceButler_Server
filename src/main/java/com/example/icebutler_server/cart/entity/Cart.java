package com.example.icebutler_server.cart.entity;

import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.global.entity.BaseEntity;
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
public class Cart extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long cardIdx;

    @OneToMany(mappedBy="cart", cascade=ALL)
    private List<Food> foods=new ArrayList<>();
    @OneToOne(cascade = ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="userIdx")
    private User owner;

    private String cartStatus;
    private boolean status;


}
