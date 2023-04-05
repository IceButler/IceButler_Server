package com.example.icebutler_server.fridge.repository;

import com.example.icebutler_server.fridge.entity.FridgeUser;
import com.example.icebutler_server.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FridgeUserRepository extends JpaRepository<FridgeUser, Long> {
  public FridgeUser findByOwner(User user);
}
