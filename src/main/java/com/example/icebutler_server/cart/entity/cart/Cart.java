package com.example.icebutler_server.cart.entity.cart;

import com.example.icebutler_server.fridge.entity.fridge.Fridge;
import com.example.icebutler_server.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
@Entity
public class Cart extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long cartIdx;

    @OneToOne
    @JoinColumn(name = "fridgeIdx", nullable = false)
    private Fridge fridge;
}
