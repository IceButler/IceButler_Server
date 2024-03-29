package com.example.icebutler_server.fridge.repository.multiFridge;

import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridge;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridgeUser;
import com.example.icebutler_server.global.entity.FridgeRole;
import com.example.icebutler_server.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MultiFridgeUserRepository extends JpaRepository<MultiFridgeUser, Long> {
  Optional<MultiFridgeUser> findByMultiFridgeAndUser_UserIdxAndRoleAndIsEnableAndUser_IsEnable(MultiFridge fridge, Long userIdx, FridgeRole fridgeRole, Boolean status, Boolean userStatus);

  Optional<MultiFridgeUser> findByMultiFridgeAndUserAndRoleAndIsEnable(MultiFridge fridge, User user, FridgeRole fridgeRole, Boolean status);

  List<MultiFridgeUser> findByMultiFridgeAndRoleAndIsEnable(MultiFridge fridge, FridgeRole fridgeRole, Boolean status);

  Optional<MultiFridgeUser> findByMultiFridgeAndUserAndIsEnable(MultiFridge fridge, User user, Boolean isEnable);

  List<MultiFridgeUser> findByMultiFridgeAndIsEnable(MultiFridge fridge, Boolean status);

  List<MultiFridgeUser> findByMultiFridgeAndIsEnableOrderByRoleDesc(MultiFridge fridge, Boolean status);

  List<MultiFridgeUser> findByUserAndIsEnable(User user, Boolean status);

  Optional<MultiFridgeUser> findByUser_UserIdxAndMultiFridge_MultiFridgeIdxAndIsEnable(Long userIdx, Long multiFridgeIdx, Boolean status);

  void deleteByMultiFridge(MultiFridge multiFridge);

  void deleteByUser(User user);
}
