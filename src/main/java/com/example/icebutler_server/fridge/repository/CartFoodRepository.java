package com.example.icebutler_server.fridge.repository;

import com.example.icebutler_server.fridge.entity.CartFood;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartFoodRepository extends JpaRepository<CartFood, Long> {
}
