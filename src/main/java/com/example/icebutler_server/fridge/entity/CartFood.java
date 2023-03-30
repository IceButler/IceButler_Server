package com.example.icebutler_server.fridge.entity;

import com.example.icebutler_server.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
@Entity
public class CartFood extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long cardFoodIdx;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="foodIdx")
    private Food food;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="cartIdx")
    private Cart cart;
    private String cartStatus;
}