package com.example.icebutler_server.cart.dto.assembler;

import com.example.icebutler_server.cart.entity.Cart;
import com.example.icebutler_server.fridge.entity.Fridge;
import org.springframework.stereotype.Component;

@Component
public class CartAssembler {

    public Cart toEntity(Fridge fridge) {
        return Cart.builder()
                .fridge(fridge)
                .build();
    }
}
