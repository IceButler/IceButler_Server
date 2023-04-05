package com.example.icebutler_server.global.util.redis.entity;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@RedisHash(value = "UserDbId", timeToLive = 2600000L)
@Getter
public class UserDbIdx {
    @Id
    private Long userIdx;
    private Long dbId;
    private LocalDateTime createdAt;

    public UserDbIdx(Long userIdx, Long dbId) {
        this.userIdx = userIdx;
        this.dbId = dbId;
        this.createdAt = LocalDateTime.now();
    }
}