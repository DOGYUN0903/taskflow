package com.taskflow.global.util;

import com.taskflow.domain.member.entity.UserRole;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    //토큰 타입 식별자
    private static final String TOKEN_TYPE = "Bearer ";

    //서명 알고리즘
    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    // 토큰 유효 시간: 1시간
    private static final long JWT_VALIDATE_TIME = 60 * 60 * 1000L;

    // 서명 키
    private final Key key;

    // JwtParser
    private final JwtParser jwtParser;

    public JwtUtil(@Value("${jwt.secret}") String secretKey){
        //시크릿 키 인코딩된 건가요? 일단 인코딩 안 된 일반 문자열 기준으로 보겠습니다.
        /*
        인코딩 된 키라면 하단 코드로 변경 예정
        byte[] byteKey = Base64.getDecoder().decode(secretKey);
         */
        // UTF-8 방식 바이트 배열로 변환
        byte[] byteKey = secretKey.getBytes(StandardCharsets.UTF_8);

        //HMAC-SHA용 시크릿 키로 초기화
        this.key = Keys.hmacShaKeyFor(byteKey);

        // 파서 생성
        jwtParser = Jwts.parserBuilder().setSigningKey(this.key).build();
    }

    // Jwt 생성
    public String issueJwt(String email, UserRole userRole){
        // 토큰 생성 시간
        Date jwtIssuedAt = new Date();
        //만료 시간: 1시간 후
        Date jwtExpireAt = new Date(jwtIssuedAt.getTime() + JWT_VALIDATE_TIME);
        //jwt 토큰 반환
        return TOKEN_TYPE +
                Jwts.builder()
                        .setSubject(email)   // 토큰 주인
                        .claim("role", userRole.getRole())    // 권한
                        .setIssuedAt(jwtIssuedAt)   // 발급일
                        .setExpiration(jwtExpireAt) // 만료일
                        .signWith(key, SIGNATURE_ALGORITHM) // 비밀키와 알고리즘으로 서명
                        .compact(); //토큰 생성
    }

    // 토큰 식별자를 제외하고 토큰만 반환
    public String getToken(String header){
        if(header != null && header.startsWith(TOKEN_TYPE))
            return header.substring(TOKEN_TYPE.length());
        //Bearer 전용 API이기 때문에 그 외의 경우 그리고 헤더가 빈 경우는 모두 토큰 없음으로 처리
        return null;
    }

    // 페이로드 반환
    public Claims getAllClaims(String token){
        return jwtParser.parseClaimsJws(token).getBody();
    }

    // 역할 반환
    // 파라미터: getToken의 반환값
    public String getRole(String token){
        return getAllClaims(token).get("role", String.class);
    }

    // 사용자 아이디 반환
    // 파라미터: getToken의 반환값
    public String getEmail(String token){
        return getAllClaims(token).getSubject();
    }

    // 토큰 유효성 검증
    // 파라미터: getToken의 반환값
    public boolean isTokenValid(String token){
        try {
            //유효하면 true, 유효하지 않으면 false(만료 등...)
            jwtParser.parseClaimsJws(token);
            return true;
        } catch (SecurityException e) {
            //log.error("서명이 올바르지 않습니다.", e);
        } catch (MalformedJwtException e) {
            //log.error("토큰 형식이 잘못되었습니다.", e);
        } catch (ExpiredJwtException e) {
            //log.error("토큰이 만료되었습니다.", e);
        } catch (UnsupportedJwtException e) {
            //log.error("지원하지 않는 토큰입니다.", e);
        } catch (IllegalArgumentException e) {
            //log.error("토큰이 비어 있거나 잘못되었습니다.", e);
        }
        return false;
    }
}
