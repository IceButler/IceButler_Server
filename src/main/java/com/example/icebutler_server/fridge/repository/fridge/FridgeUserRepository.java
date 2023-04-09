package com.example.icebutler_server.fridge.repository.fridge;

import com.example.icebutler_server.fridge.entity.fridge.Fridge;
import com.example.icebutler_server.fridge.entity.fridge.FridgeUser;
import com.example.icebutler_server.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FridgeUserRepository extends JpaRepository<FridgeUser, Long> {
    Optional<Object> findByUserAndFridge(User user, Fridge fridge);
}
