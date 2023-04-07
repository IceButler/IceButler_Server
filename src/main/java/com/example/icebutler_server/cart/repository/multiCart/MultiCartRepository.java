package com.example.icebutler_server.cart.repository.multiCart;

import com.example.icebutler_server.cart.entity.multiCart.MultiCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MultiCartRepository extends JpaRepository<MultiCart, Long> {
}
