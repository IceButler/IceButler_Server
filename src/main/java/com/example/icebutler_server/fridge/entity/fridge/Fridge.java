package com.example.icebutler_server.fridge.entity.fridge;

import com.example.icebutler_server.cart.entity.cart.Cart;
import com.example.icebutler_server.global.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Builder
public class Fridge extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long fridgeIdx;
    private String fridgeName;
    private String fridgeComment;

    @OneToOne
    @JoinColumn(name = "cartIdx")
    private Cart cart;


    public void updateNameAndComment(Fridge toUpdateEntity) {
        this.fridgeName = toUpdateEntity.getFridgeName();
        this.fridgeComment = toUpdateEntity.getFridgeComment();
    }

}
