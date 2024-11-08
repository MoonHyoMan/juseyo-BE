package com.moonhyoman.juseyo_be.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtGenerator {
    private final Key key;

    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30; // 30 minutes
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7; // 7 days
    private static final String GRANT_TYPE = "Bearer "; // Grant type

    // application.yml에서 secret 값 가져와서 key에 저장
    public JwtGenerator(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        log.info("Generated Key: {}", key);
    }

    // Member 정보를 가지고 AccessToken, RefreshToken을 생성하는 메서드
    public JwtToken generateToken(String userId) {
        try {
            long now = (new Date()).getTime();
            Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);

            String accessToken = Jwts.builder()
                    .setSubject(String.valueOf(userId))
                    .claim("auth", "ROLE_USER,ROLE_ADMIN")
                    .setIssuedAt(new Date(now))
                    .setExpiration(accessTokenExpiresIn)
                    .signWith(key, SignatureAlgorithm.HS256)
                    .compact();

            String refreshToken = Jwts.builder()
                    .setIssuedAt(new Date(now))
                    .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
                    .signWith(key, SignatureAlgorithm.HS256)
                    .compact();

            JwtToken jwtToken = JwtToken.builder()
                    .grantType(GRANT_TYPE)
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();

            log.info("#################################");
            log.info("jwtToken : {}", jwtToken.toString());
            log.info("#################################");

            return jwtToken;
        } catch (Exception e) {
            System.err.println("토큰 생성 중 오류 발생: " + e.getMessage());
            e.printStackTrace(); // 예외의 전체 스택 트레이스를 출력
            return null; // 예외 발생 시 null 반환
        }
    }

}
