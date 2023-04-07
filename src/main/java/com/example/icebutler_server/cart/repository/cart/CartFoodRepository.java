package com.example.icebutler_server.cart.repository.cart;

import com.example.icebutler_server.cart.entity.cart.CartFood;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartFoodRepository extends JpaRepository<CartFood, Long>, CartFoodQuerydslRepository {
    Optional<CartFood> findByCardFoodIdx(Long cardIdx);
}
