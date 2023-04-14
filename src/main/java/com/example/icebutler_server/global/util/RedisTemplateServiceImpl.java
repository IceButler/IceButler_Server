//package com.example.icebutler_server.global.util;
//
//import com.sun.istack.NotNull;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.ValueOperations;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//
//
//
//@RequiredArgsConstructor
//@Service
//public class RedisTemplateServiceImpl implements RedisTemplateService {
//
//    private final RedisTemplate<Long, String> redisTemplate;
//
//    public void deleteUserRefreshToken(@NotNull Long userIdx){
//        ValueOperations<Long, String> valueOperations = redisTemplate.opsForValue();
//        valueOperations.getAndDelete(userIdx);
//    }
//
////    @Nullable
//    @Transactional(readOnly = true)
//    public String getUserRefreshToken(@NotNull Long userIdx) {
//        ValueOperations<Long, String> valueOperations = redisTemplate.opsForValue();
//        return valueOperations.get(userIdx);
//    }
//}
