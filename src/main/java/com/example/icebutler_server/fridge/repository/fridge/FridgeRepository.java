package com.example.icebutler_server.fridge.repository.fridge;

import com.example.icebutler_server.fridge.entity.fridge.Fridge;
import com.example.icebutler_server.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FridgeRepository extends JpaRepository<Fridge, Long> {
//  Fridge findByFridgeIdxAndOwner(Long fridgeId, User user);
}
