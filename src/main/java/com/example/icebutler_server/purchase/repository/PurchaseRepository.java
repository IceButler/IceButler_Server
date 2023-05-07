package com.example.icebutler_server.purchase.repository;

import com.example.icebutler_server.purchase.entity.PurchaseHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends JpaRepository<PurchaseHistory, Long> {
}
