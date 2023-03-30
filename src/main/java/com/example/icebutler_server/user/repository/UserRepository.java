package com.example.icebutler_server.user.repository;

import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  public User findByNickname(String nickName);
}
