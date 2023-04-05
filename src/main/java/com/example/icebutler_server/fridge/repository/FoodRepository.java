package com.example.icebutler_server.fridge.repository;

import com.example.icebutler_server.fridge.entity.Food;
import com.example.icebutler_server.fridge.entity.FoodCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.zip.ZipFile;

public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findAllByFoodIdxIn(List<Long> foodIdxes);

    List<Food> findAllByFoodCategory(FoodCategory foodCategory);
}
