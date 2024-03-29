package com.example.icebutler_server.cart.entity.cart;

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
@SQLDelete(sql = "UPDATE cart_food SET is_enable = false, update_at = current_timestamp WHERE cart_food_idx = ?")
public class CartFood extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long cartFoodIdx;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="foodIdx")
    private Food food;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="cartIdx")
    private Cart cart;

    @Builder
    public CartFood(Food food, Cart cart) {
        this.food = food;
        this.cart = cart;
    }

}
