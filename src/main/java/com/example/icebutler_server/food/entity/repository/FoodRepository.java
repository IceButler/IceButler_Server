package com.example.icebutler_server.food.entity.repository;

import com.example.icebutler_server.food.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FoodRepository extends JpaRepository<Food,Integer> {
    Optional<Food> findByFoodIdx(Food food);

}
