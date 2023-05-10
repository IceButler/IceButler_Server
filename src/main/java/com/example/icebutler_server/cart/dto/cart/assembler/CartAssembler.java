package com.example.icebutler_server.cart.dto.cart.assembler;

import com.example.icebutler_server.cart.entity.cart.Cart;
import com.example.icebutler_server.fridge.entity.fridge.Fridge;
import org.springframework.stereotype.Component;

@Component
public class CartAssembler {

    public Cart toEntity(Fridge fridge) {
        return Cart.builder()
                .fridge(fridge)
                .build();
    }
}
