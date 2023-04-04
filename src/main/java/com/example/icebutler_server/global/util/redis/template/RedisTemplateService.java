package com.example.icebutler_server.global.util.redis.template;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class RedisTemplateService {
    public void deleteUserRefreshToken(@NotNull Long userId){
//        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
//        valueOperations.getAndDelete(userId);
    }
}
