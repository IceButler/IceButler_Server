package com.example.icebutler_server.cart.repository.cart;

import com.example.icebutler_server.cart.entity.cart.Cart;
import com.example.icebutler_server.fridge.entity.fridge.Fridge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByFridge_FridgeIdxAndIsEnable(Long fridgeIdx, boolean status);
}
