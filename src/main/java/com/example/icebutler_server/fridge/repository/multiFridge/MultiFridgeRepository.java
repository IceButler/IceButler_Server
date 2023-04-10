package com.example.icebutler_server.fridge.repository.multiFridge;

import com.example.icebutler_server.food.entity.FoodCategory;
import com.example.icebutler_server.fridge.entity.fridge.FridgeFood;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MultiFridgeRepository extends JpaRepository<MultiFridge, Long> {
    Optional<MultiFridge> findByMultiFridgeIdxAndIsEnable(Long fridgeIdx, Boolean status);

//  Fridge findByFridgeIdxAndOwner(Long fridgeId, User user);
}
