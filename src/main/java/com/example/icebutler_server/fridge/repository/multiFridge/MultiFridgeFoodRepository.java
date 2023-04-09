package com.example.icebutler_server.fridge.repository.multiFridge;

import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridgeFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MultiFridgeFoodRepository extends JpaRepository<MultiFridgeFood, Long> {

}
