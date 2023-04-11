package com.example.icebutler_server.fridge.repository.multiFridge;

import com.example.icebutler_server.food.entity.FoodCategory;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridge;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridgeFood;
import com.example.icebutler_server.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MultiFridgeFoodRepository extends JpaRepository<MultiFridgeFood, Long> {
    List<MultiFridgeFood> findByFood_FoodCategoryAndIsEnableOrderByShelfLife(FoodCategory foodCategory, Boolean isEnable);
    List<MultiFridgeFood> findByIsEnableOrderByShelfLife(Boolean status);
    Optional<MultiFridgeFood> findByMultiFridgeFoodIdxAndOwnerAndMultiFridgeAndIsEnable(Long multiFridgeFoodIdx, User owner, MultiFridge multiFridge, Boolean isEnable);

}
