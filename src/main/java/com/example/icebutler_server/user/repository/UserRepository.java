package com.example.icebutler_server.user.repository;

import com.example.icebutler_server.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUserIdxAndIsEnable(Long userIdx, Boolean status);
  User findByNickname(String nickName);

}
