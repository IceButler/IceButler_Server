package com.example.icebutler_server.user.repository;

import com.example.icebutler_server.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  public User findByNickname(String nickName);
  Optional<User> findByUserIdx(Long userIdx);
}
