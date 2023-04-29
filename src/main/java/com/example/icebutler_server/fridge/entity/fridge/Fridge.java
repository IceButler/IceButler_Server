package com.example.icebutler_server.fridge.entity.fridge;

import com.example.icebutler_server.cart.entity.cart.Cart;
import com.example.icebutler_server.global.entity.BaseEntity;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
@Entity
@SQLDelete(sql = "UPDATE fridge SET is_enable = false, last_modified_date = current_timestamp WHERE fridge_idx = ?")
public class Fridge extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long fridgeIdx;

    @Column(nullable = false)
    private String fridgeName;

    private String fridgeComment;

    @OneToOne
    @JoinColumn(name = "cartIdx")
    private Cart cart;

    @Builder
    public Fridge(
                  String fridgeName,
                  String fridgeComment) {
        this.fridgeName = fridgeName;
        this.fridgeComment = fridgeComment;
    }

    public void updateBasicFridgeInfo(String fridgeName, String fridgeComment) {
        this.fridgeName = fridgeName;
        this.fridgeComment = fridgeComment;
    }

    public void remove() {
        this.setIsEnable(false);
    }
}
