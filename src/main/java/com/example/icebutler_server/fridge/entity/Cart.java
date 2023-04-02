package com.example.icebutler_server.fridge.entity;

import com.example.icebutler_server.user.entity.User;
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
public class Cart extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long cardIdx;
    @OneToMany(mappedBy="cart", cascade=ALL)
    private List<CartFood> cartFoods = new ArrayList<>();
    @OneToOne(cascade = ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "userIdx")
    private User owner;
    private String cartStatus;

    @Builder
    public Cart(User owner) {
        this.owner = owner;
        owner.addCart(this);
    }

    public void addCartFood(CartFood cartFood) {
        this.cartFoods.add(cartFood);
    }

    public void removeCartFood(CartFood cartFood) {
        this.cartFoods.removeIf((cf) -> cf.getCardFoodIdx().equals(cartFood.getCardFoodIdx()));
    }
}
