package com.example.icebutler_server.fridge.repository;

import com.example.icebutler_server.fridge.entity.Fridge;
import com.example.icebutler_server.fridge.entity.FridgeUser;
import com.example.icebutler_server.global.entity.FridgeRole;
import com.example.icebutler_server.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FridgeUserRepository extends JpaRepository<FridgeUser, Long> {

    Optional<FridgeUser> findByUserAndFridgeAndIsEnable(User user, Fridge fridge, Boolean isEnable);
    Optional<FridgeUser> findByUserIdAndFridgeIdAndIsEnable(Long userId, Long fridgeId, Boolean isEnable);

    Optional<FridgeUser> findByFridgeAndUserIdAndRoleAndIsEnable(Fridge fridge, Long userId, FridgeRole fridgeRole, Boolean status);

    List<FridgeUser> findByFridgeAndIsEnable(Fridge fridge, Boolean isEnable);

    boolean existsByFridgeAndRoleAndIsEnable(Fridge fridge, FridgeRole role, boolean isEnable);

    Optional<FridgeUser> findByFridgeIdAndUserIdAndIsEnable(Long fridgeId, Long userId, boolean isEnable);

    Optional<FridgeUser> findByUserIdAndIsEnable(Long userId, Boolean status);

    Optional<FridgeUser> findByFridgeAndUserAndIsEnable(Fridge fridge, User user, Boolean isEnable);

    void deleteByFridge(Fridge fridge);

    void deleteByUser(User user);

    void deleteByFridgeAndUserIn(Fridge fridge, List<User> user);

    List<FridgeUser> findByUserAndRoleAndIsEnable(User user, FridgeRole role, Boolean isEnable);

    List<FridgeUser> findByFridgeAndRoleAndIsEnable(Fridge fridge, FridgeRole role, Boolean isEnable);

}
