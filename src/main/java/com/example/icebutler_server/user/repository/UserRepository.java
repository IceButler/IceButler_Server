package com.example.icebutler_server.user.repository;

import com.example.icebutler_server.user.entity.Provider;
import com.example.icebutler_server.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUserIdxAndIsEnable(Long userIdx, Boolean isEnable);
  User findByEmailAndProvider(String email, Provider provider);
  Boolean existsByNickname(String nickName);
}
