package com.example.icebutler_server.admin.repository;

import com.example.icebutler_server.admin.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByEmail(String email);
    Optional<Admin> findByAdminIdxAndIsEnable(Long adminIdx, boolean isEnable);
}
