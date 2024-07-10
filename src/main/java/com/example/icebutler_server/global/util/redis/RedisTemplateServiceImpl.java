package com.example.icebutler_server.global.util.redis;

import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class RedisTemplateServiceImpl implements RedisTemplateService {

    private final RedisTemplate<String, String> redisTemplate;

    public void deleteUserRefreshToken(String userId){
        if(redisTemplate.opsForValue().get(userId)!=null) redisTemplate.delete(userId);
    }

//    @Nullable
    @Transactional(readOnly = true)
    public String getUserRefreshToken(@NotNull String userId) {
        return redisTemplate.opsForValue().get(userId);
    }

    public void setUserRefreshToken(@NotNull String userId, String refreshToken) {
        redisTemplate.opsForValue().set(userId, refreshToken);
    }
}
