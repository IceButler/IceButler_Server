package com.example.icebutler_server.global.util.redis.template;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class RedisTemplateService {

    private final RedisTemplate<Long, Long> redisTemplate;

    public void deleteUserRefreshToken(@NotNull Long userId){
        ValueOperations<Long, Long> valueOperations = redisTemplate.opsForValue();
        valueOperations.getAndDelete(userId);
    }
}
