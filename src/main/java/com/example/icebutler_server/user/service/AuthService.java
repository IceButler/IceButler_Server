package com.example.icebutler_server.user.service;

import com.example.icebutler_server.user.dto.response.PostUserRes;
import com.example.icebutler_server.user.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final int accessTokenExpiryDate = 604800000;
  private final int refreshTokenExpiryDate = 604800000;

  private final RedisTemplate<String, String> redisTemplate;

  @Value("${jwt.secret}")
  private String key;

  public PostUserRes createToken(User user) {
    String accessToken = createAccessToken(user.getUserIdx());
    String refreshToken = createRefreshToken(user.getUserIdx());
    return new PostUserRes(accessToken, refreshToken);
  }

  public String createAccessToken(Long userIdx) {
    Date now = new Date();
    return Jwts.builder()
            .claim("userIdx", userIdx)
            .setSubject(userIdx.toString())
            .setExpiration(new Date(now.getTime() + accessTokenExpiryDate))
            .signWith(SignatureAlgorithm.HS256, key)
            .compact();
  }

  public String createRefreshToken(Long userIdx) {
    Date now = new Date();
    String refreshToken = Jwts.builder()
            .setExpiration(new Date(now.getTime() + refreshTokenExpiryDate))
            .signWith(SignatureAlgorithm.HS256, key)
            .compact();
    redisTemplate.opsForValue().set(String.valueOf(userIdx), refreshToken, Duration.ofMillis(refreshTokenExpiryDate));
    return refreshToken;
  }




}
