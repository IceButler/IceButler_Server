package com.example.icebutler_server.fridge.dto.assembler;

import com.example.icebutler_server.fridge.entity.Cart;
import com.example.icebutler_server.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class CartAssembler {
    public Cart toEntity(User owner) {
        return Cart.builder().owner(owner).build();
    }
}
