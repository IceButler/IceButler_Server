package com.example.icebutler_server.fridge.repository.fridge;

import com.example.icebutler_server.fridge.entity.fridge.Fridge;
import com.example.icebutler_server.fridge.entity.fridge.FridgeUser;
import com.example.icebutler_server.global.entity.FridgeRole;
import com.example.icebutler_server.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FridgeUserRepository extends JpaRepository<FridgeUser, Long> {

  Optional<Object> findByUserAndFridgeAndIsEnable(User user, Fridge fridge, Boolean isEnable);

  Optional<FridgeUser> findByFridgeAndUserAndRoleAndIsEnable(Fridge fridge, User user, FridgeRole fridgeRole, Boolean status);

  Optional<FridgeUser> findByFridgeAndUser_UserIdxAndRoleAndIsEnableAndUser_IsEnable(Fridge fridge, Long userIdx, FridgeRole fridgeRole, Boolean status, Boolean userStatus);

  List<FridgeUser> findByFridgeAndIsEnable(Fridge fridge, Boolean isEnable);

  List<FridgeUser> findByUserAndIsEnable(User user, Boolean status);

  Optional<FridgeUser> findByFridgeAndUserAndIsEnable(Fridge fridge, User user, Boolean isEnable);

}
