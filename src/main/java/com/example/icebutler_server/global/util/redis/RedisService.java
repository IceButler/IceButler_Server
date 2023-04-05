//package com.example.icebutler_server.global.util.redis;
//
////import com.example.icebutler_server.global.util.redis.repository.UserRefreshTokenRepository;
//import com.sun.istack.NotNull;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.lang.Nullable;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//
//@Transactional
//@Service
//public class RedisService {
//
////    @Autowired
////    private UserDbIdRepository dbIdRepository;
//
//    @Autowired
//    private UserRefreshTokenRepository refreshTokenRepository;
//
//    /**
//     * 토큰 삭제 기능
//     *
//     */
//    public void deleteUserRefreshToken(@NotNull Long userIdx){
//        refreshTokenRepository.deleteById(userIdx);
//    }
//
//}