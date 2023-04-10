package com.example.icebutler_server.fridge.entity.fridge;

import com.example.icebutler_server.cart.entity.cart.Cart;
import com.example.icebutler_server.global.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
@Entity
public class Fridge extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long fridgeIdx;
    private String fridgeName;
    private String fridgeComment;

    @OneToOne
    @JoinColumn(name = "cartIdx")
    private Cart cart;

    public void addFridgeUser(FridgeUser fridgeUser){
        this.fridgeUsers.add(fridgeUser);
    }

    @Builder
    public Fridge(
                  String fridgeName,
                  String fridgeComment,
                  FridgeUser fridgeUser ) {
        this.fridgeName = fridgeName;
        this.fridgeComment = fridgeComment;
//        this.fridgeUsers.add(fridgeUser);
//        fridgeUser.addFridge(this);
    }

}
