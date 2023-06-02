package com.shop.market.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TokenProvider {

    private static final String AuthorizationHeader = "Authorization";
    private final long ACCESS_TOKEN_EXPIRE_LENGTH;
    private final long REFRESH_TOKEN_EXPIRE_LENGTH;
    private final String tokenType;
    private final Key key;

    public TokenProvider() {
        this.key = JwtProperties.secretKey;
        this.tokenType = "Bearer ";
        this.ACCESS_TOKEN_EXPIRE_LENGTH = JwtProperties.accessTime;
        this.REFRESH_TOKEN_EXPIRE_LENGTH = JwtProperties.refreshTime;
    }

    // Token 만료시간
    private Date expireTime(long expire) {
        Date now = new Date();
        return new Date(now.getTime() + expire);
    }

    // Get Claims
    private Claims getClaims(String token){
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody();
    }

    private String jwtToken(Date expire,String username){
        // 현재 시간
        Date now = new Date();

        // Header 지정
        Map<String,Object> headers = new HashMap<>();
        headers.put("typ","JWT");
        headers.put("alg","HS512");

        // payload key:value 지정 (claim 들)
        Claims claims = Jwts.claims()
                .setIssuedAt(now)
                .setExpiration(expire);
        claims.put("role","ROLE_USER");

        // jwt 생성
        String JwtToken = Jwts.builder()
                .setHeader(headers)
                .setClaims(claims)
                .setSubject(username)
                .signWith(key)
                .compact();

        // tokenType 추가 ( bearer )
//        JwtToken = tokenType + JwtToken;

        return JwtToken;
    }

    //ACCESS TOKEN 발급
    public String getAccessToken(String username) {
        // ACCESS TOKEN 만료 시간
        Date expire = expireTime(ACCESS_TOKEN_EXPIRE_LENGTH);

        String JwtToken = jwtToken(expire,username);

        log.info(String.format("accessToken : %s", JwtToken));

        return JwtToken;
    }

    //REFRESH TOKEN 발급
    public String getReFreshToken(String username) {
        Date expire = expireTime(REFRESH_TOKEN_EXPIRE_LENGTH);

        String JwtToken = jwtToken(expire,username);

//        JwtToken = tokenType + JwtToken;
        log.info(String.format("refreshToken : %s", JwtToken));
        return JwtToken;
    }

    // 권한 인증
    public Authentication getAuthentication(String token) {
        // payload parse 하여 claim 반환
        Claims claims = getClaims(token);
        String role = claims.get("role").toString();
        String username = claims.getSubject();

        log.info(String.format("getAuthentication role : %s", role));
        log.info(String.format("getAuthentication username : %s", username));

        // 권한 부여
        return new UsernamePasswordAuthenticationToken("username", null,
                Collections.singleton(new SimpleGrantedAuthority(role)));
    }

    // TOKEN 확인
    public boolean validationToken(String token) {
        try {
            if (token != null) {
                // payload 의 정보 key:value 한쌍 = claim
                // token 에서 claim 추출
                Claims claims = getClaims(token);
                // 만료시간 확인
                return claims.getExpiration().after(new Date());
            }
            return false;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다");
        } catch (UnsupportedJwtException e) {
            log.info("지원하지 않는 JWT 토큰입니다");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다");
        }
        return false;
    }

    // COOKIE 에서 TOKEN 가져옴
    public HashMap<String, String> resolveToken(HttpServletRequest request) {
        Cookie[] tokens = request.getCookies();
        HashMap<String, String> Tokens = new HashMap<>();
        try {
            for (Cookie cookie : tokens) {
                String name = cookie.getName();
                if (name.equals("accessToken")) {
                    Tokens.put("accessToken", cookie.getValue());
                } else if (name.equals("refreshToken")) {
                    Tokens.put("refreshToken", cookie.getValue());
                }
            }
            return Tokens;
        } catch(Exception e) {
            log.info("resolve error");
        }
        return null;
    }

    // HEADER 에서 TOKEN 가져옴
//    public String resolveToken(HttpServletRequest request){
//        String bearerToken = request.getHeader(AuthorizationHeader);
//        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(tokenType)){
//            return bearerToken.substring(7);
//        }
//        return null;
//    }

    public String getUsername(String token){
        Claims claims = getClaims(token);
        return claims.getSubject();
    }
}
