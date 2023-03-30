package com.example.icebutler_server.fridge.repository;

import com.example.icebutler_server.fridge.entity.Fridge;
import com.example.icebutler_server.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FridgeRepository extends JpaRepository<Fridge, Long> {


}
