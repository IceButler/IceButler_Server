package com.example.icebutler_server.cart.repository;

import com.example.icebutler_server.cart.entity.Cart;
import com.example.icebutler_server.fridge.entity.Fridge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByFridge_IdAndIsEnable(Long fridgeId, boolean status);
    void deleteByFridge(Fridge fridge);
}
