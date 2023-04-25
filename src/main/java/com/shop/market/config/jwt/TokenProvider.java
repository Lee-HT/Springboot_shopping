package com.shop.market.config.jwt;

import com.shop.market.dto.userD;
import com.shop.market.repository.UserMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
public class TokenProvider {
    private static final String AUTHORITIES_KEY = "NeighborAPI";
    private final long ACCESS_TOKEN_EXPIRE_LENGTH;
    private final long REFRESH_TOKEN_EXPIRE_LENGTH;
    private static final String AuthorizationHeader = "Authorization";
    private final String secretKey;
    private final UserMapper userMapper;
    private Key key;

    public TokenProvider(UserMapper userMapper) {
        this.userMapper = userMapper;
        this.secretKey = Base64.getEncoder().encodeToString(JwtProperties.secretKey.getBytes());
        this.ACCESS_TOKEN_EXPIRE_LENGTH = JwtProperties.accessTime;
        this.REFRESH_TOKEN_EXPIRE_LENGTH = JwtProperties.refreshTime;
    }
    public String CreateToken(Authentication authentication){
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        log.info(authorities);

        Date now = new Date();

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_LENGTH))
                .compact();
    }

    public Authentication getAuthentication(String token) {
        String username = Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build().parseClaimsJws(token).getBody().getSubject();
        userD user = userMapper.findUserByUsername(username);
        Collection<? extends GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority(user.getRole()));
        return new UsernamePasswordAuthenticationToken(user,token,authorities);
    }

    public boolean validationToken(String token) {
        try{
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
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

    public String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader(AuthorizationHeader);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }
}
