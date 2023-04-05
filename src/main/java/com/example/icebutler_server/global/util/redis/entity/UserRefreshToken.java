package com.example.icebutler_server.global.util.redis.entity;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@RedisHash(value = "UserRefreshToken", timeToLive = 2600000L)
@Getter
public class UserRefreshToken {

    @Id
    private Long userIdx;
    private String refreshToken;
    private LocalDateTime createdAt;

    public UserRefreshToken(Long userIdx, String refreshToken) {
        this.userIdx = userIdx;
        this.refreshToken = refreshToken;
        this.createdAt = LocalDateTime.now();
    }
}