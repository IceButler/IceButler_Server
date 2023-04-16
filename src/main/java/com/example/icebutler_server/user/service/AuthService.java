package com.example.icebutler_server.user.service;

import com.example.icebutler_server.user.dto.response.PostUserRes;
import com.example.icebutler_server.user.entity.User;
import com.example.icebutler_server.user.exception.TokenExpirationException;
import com.example.icebutler_server.user.exception.TokenInvalidException;
import com.example.icebutler_server.user.exception.TokenMalformedException;
import com.example.icebutler_server.user.exception.TokenUnSupportedException;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.Date;

import static com.example.icebutler_server.global.Constant.Auth.*;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final int accessTokenExpiryDate = 604800000;
  private final int refreshTokenExpiryDate = 604800000;

  private final RedisTemplate<String, String> redisTemplate;

  @Value("${jwt.secret}")
  private String key;

  public Long getUserIdx() {
    String token = getToken();
    if(token==null) throw new TokenExpirationException();
    return getClaims(token).getBody().get(CLAIM_NAME, Long.class);
  }
  // 토큰 추출
  private String getToken() {
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    String token = request.getHeader(REQUEST_HEADER_NAME);
    if (token == null) return null;
//    if(redisTemplate.opsForValue().get(token)!=null) throw new BaseException(INVALID_TOKEN);
    return token;
  }
  public Jws<Claims> getClaims(String token) {
    Jws<Claims> claims = null;
    token = token.replaceAll(TOKEN_REGEX, TOKEN_REPLACEMENT);
    try {
      claims = Jwts.parser()
              .setSigningKey(key)
              .parseClaimsJws(token);
    } catch (ExpiredJwtException expiredJwtException) {
      throw new TokenExpirationException();
    } catch (MalformedJwtException malformedJwtException) {
      throw new TokenMalformedException();
    } catch (UnsupportedJwtException unsupportedJwtException) {
      throw new TokenUnSupportedException();
    } catch (Exception e) {
      throw new TokenInvalidException();
    }
    return claims;
  }

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
//    redisTemplate.opsForValue().set(String.valueOf(userIdx), refreshToken, Duration.ofMillis(refreshTokenExpiryDate));
    return refreshToken;
  }




}
