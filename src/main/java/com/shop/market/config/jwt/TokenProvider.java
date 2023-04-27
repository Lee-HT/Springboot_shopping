package com.shop.market.config.jwt;

import com.shop.market.dto.userD;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
public class TokenProvider {
    private static final String AuthorizationHeader = "Authorization";
    private final long ACCESS_TOKEN_EXPIRE_LENGTH;
    private final long REFRESH_TOKEN_EXPIRE_LENGTH;
    private final Key key;

    public TokenProvider() {
        this.key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        this.ACCESS_TOKEN_EXPIRE_LENGTH = JwtProperties.accessTime;
        this.REFRESH_TOKEN_EXPIRE_LENGTH = JwtProperties.refreshTime;
    }
    // Token 만료시간
    private Date expireTime(Date now, long expire){
        return new Date(now.getTime() + expire);
    }

    //ACCESS TOKEN 발급
    public String CreateToken(userD user){
        // 현재 시간
        Date now = new Date();
        // 만료 시간
        Date expire = expireTime(now,ACCESS_TOKEN_EXPIRE_LENGTH);

        // Header 지정
        Map<String,Object> headers = new HashMap<>();
        headers.put("typ","JWT");
        headers.put("alg","HS512");

        // payload key:value 지정 (Claim 들)
        Claims claims = Jwts.claims()
                .setSubject(user.getUsername())
                .setIssuedAt(now)
                .setExpiration(expire);
        claims.put("role","ROLE_USER");

        // jwt 생성
        String JwtToken = Jwts.builder()
                .setHeader(headers)
                .setClaims(claims)
                .signWith(key,SignatureAlgorithm.HS512)
                .compact();
        log.info(JwtToken);
        return JwtToken;
    }

    //REFRESH TOKEN 발급
    public String ReFreshToken(){
        Date now = new Date();
        Date expire = expireTime(now,REFRESH_TOKEN_EXPIRE_LENGTH);

        return Jwts.builder()
                .setExpiration(expire)
                .signWith(key)
                .compact();
    }

    // 권한 인증
    public Authentication getAuthentication(String token) {
        // payload parse 하여 string 변환
        String role = Jwts.parserBuilder()
                .setSigningKey(key)
                .build().parseClaimsJws(token).getBody().get("role").toString();

        // 권한 부여
//        Collection<? extends GrantedAuthority> authorities =
//                Collections.singleton(new SimpleGrantedAuthority(user.getRole()));
        return new JwtAuthentication(role);
    }

    // TOKEN 확인
    public boolean validationToken(String token) {
        try{
            // payload 의 정보 key:value 한쌍 = claim
            // token 에서 claim 추출
            Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
            // 만료시간 확인
            return !claims.getExpiration().before(new Date());
        }catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e){
            log.info("잘못된 JWT 서명입니다.");
        }catch (ExpiredJwtException e){
            log.info("만료된 JWT 토큰입니다");
        }catch (UnsupportedJwtException e){
            log.info("지원하지 않는 JWT 토큰입니다");
        }catch (IllegalArgumentException e){
            log.info("JWT 토큰이 잘못되었습니다");
        }
        return false;
    }

    // HEADER 에서 TOKEN 가져옴
    public String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader(AuthorizationHeader);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }
}
