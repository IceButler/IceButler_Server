package com.example.icebutler_server.purchase.repository;

import com.example.icebutler_server.purchase.entity.PurchaseHistory;
import com.example.icebutler_server.purchase.entity.Subscription;
import com.example.icebutler_server.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PurchaseRepository extends JpaRepository<PurchaseHistory, Long> {

  PurchaseHistory findByUserAndSubscription(User user, Subscription subscription);
}
