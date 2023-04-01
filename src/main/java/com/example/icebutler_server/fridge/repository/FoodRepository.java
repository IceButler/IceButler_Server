package com.example.icebutler_server.fridge.repository;

import com.example.icebutler_server.fridge.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findAllByFoodIdxIn(List<Long> foodIdxes);
}
