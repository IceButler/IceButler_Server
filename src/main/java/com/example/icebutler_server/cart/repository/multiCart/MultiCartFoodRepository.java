package com.example.icebutler_server.cart.repository.multiCart;

import com.example.icebutler_server.cart.entity.cart.CartFood;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MultiCartFoodRepository extends JpaRepository<CartFood, Long> {
}
