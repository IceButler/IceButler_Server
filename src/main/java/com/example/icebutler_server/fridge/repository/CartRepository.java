package com.example.icebutler_server.fridge.repository;

import com.example.icebutler_server.fridge.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
