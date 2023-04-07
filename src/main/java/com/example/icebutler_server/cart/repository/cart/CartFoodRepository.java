package com.example.icebutler_server.cart.repository.cart;

import com.example.icebutler_server.cart.entity.cart.Cart;
import com.example.icebutler_server.cart.entity.cart.CartFood;
import com.example.icebutler_server.cart.repository.cart.CartFoodQuerydslRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartFoodRepository extends JpaRepository<CartFood, Long>, CartFoodQuerydslRepository {
    List<CartFood> findByCart(Cart cart);
}
