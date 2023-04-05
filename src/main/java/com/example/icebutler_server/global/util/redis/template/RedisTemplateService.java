//package com.example.icebutler_server.global.util.redis.template;

//import com.example.icebutler_server.global.util.redis.RedisService;
//import com.sun.istack.NotNull;

//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.ValueOperations;
//import org.springframework.stereotype.Service;



//@RequiredArgsConstructor
//@Service
//public class RedisTemplateService {

//    private final RedisTemplate<Long, String> redisTemplate;
//
//    /**
//     * 토큰 삭제 기능
//     *
//     */
//    public void deleteUserRefreshToken(@NotNull Long userIdx){
//        ValueOperations<Long, String> valueOperations = redisTemplate.opsForValue();
//        valueOperations.getAndDelete(userIdx);
//    }
//}