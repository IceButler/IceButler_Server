package com.example.icebutler_server.cart.repository.multiCart;

import com.example.icebutler_server.cart.entity.multiCart.MultiCart;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridgeUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MultiCartRepository extends JpaRepository<MultiCart, Long> {
    Optional<MultiCart> findByMultiFridgeUserAndIsEnable(MultiFridgeUser fridgeUser, Boolean status);
}
