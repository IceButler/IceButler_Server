package com.example.icebutler_server.fridge.repository.fridge;

import com.example.icebutler_server.fridge.entity.fridge.Fridge;
import com.example.icebutler_server.fridge.entity.fridge.FridgeUser;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridge;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridgeUser;
import com.example.icebutler_server.global.entity.FridgeRole;
import com.example.icebutler_server.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public interface FridgeUserRepository extends JpaRepository<FridgeUser, Long> {

  Optional<Object> findByUserAndFridge(User user, Fridge fridge);

  Optional<FridgeUser> findByFridgeAndUserAndRoleAndIsEnable(Fridge fridge, User user, FridgeRole fridgeRole, Boolean status);

  Optional<FridgeUser> findByFridgeAndUser_UserIdxAndRoleAndIsEnableAndUser_IsEnable(Fridge fridge, Long userIdx, FridgeRole fridgeRole, Boolean status, Boolean userStatus);

  List<FridgeUser> findByFridgeAndIsEnable(Fridge fridge, Boolean isEnable);

//  List<FridgeUser> findByFridge(Fridge fridge);
List<FridgeUser> findByFridge(User user);

  List<FridgeUser> findByUser(User user);
}
