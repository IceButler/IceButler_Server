package com.example.icebutler_server.fridge.entity;

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
    private Long cartIdx;

    @OneToMany(mappedBy="cart", cascade=ALL)
    private List<CartFood> cartFoods = new ArrayList<>();

    @OneToOne(cascade = ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "userIdx")
    private User owner;

    @OneToOne
    private Fridge fridge;

    private String cartStatus;

    public void addCartFood(CartFood cartFood) {
        this.cartFoods.add(cartFood);
    }
}
