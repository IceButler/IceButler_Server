package com.example.icebutler_server.cart.entity.cart;

import com.example.icebutler_server.fridge.entity.fridge.Fridge;
import com.example.icebutler_server.global.entity.BaseEntity;
import com.example.icebutler_server.global.entityListener.CartEntityListener;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
@Entity
@SQLDelete(sql = "UPDATE cart SET is_enable = false, update_at = current_timestamp WHERE cart_idx = ?")
@EntityListeners(CartEntityListener.class)
public class Cart extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long cartIdx;

    @OneToOne
    @JoinColumn(name = "fridgeIdx", nullable = false)
    private Fridge fridge;

    @Builder
    public Cart(Fridge fridge) {
        this.fridge = fridge;
    }

    public static Cart toEntity(Fridge fridge) {
        return Cart.builder()
                .fridge(fridge)
                .build();
    }
}
