package com.example.icebutler_server.cart.entity;

import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
@Entity
@SQLDelete(sql = "UPDATE cart_food SET is_enable = false, updated_at = current_timestamp WHERE id = ?")
public class CartFood extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="food_id")
    private Food food;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="cart_id")
    private Cart cart;

    @Builder
    public CartFood(Food food, Cart cart) {
        this.food = food;
        this.cart = cart;
    }

    public static CartFood toEntity(Cart cart, Food food) {
        return CartFood.builder()
                .cart(cart)
                .food(food)
                .build();
    }
}
