package com.example.icebutler_server.user.repository;

import com.example.icebutler_server.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserIdx(Long userIdx);
    Optional<User> findByNickname(String nickName);
    Optional<User> findByPhoneNum(String phoneNum);
    List<User> findByNicknameStartsWithAndUserIdNot(String nickName, String userId);


}
