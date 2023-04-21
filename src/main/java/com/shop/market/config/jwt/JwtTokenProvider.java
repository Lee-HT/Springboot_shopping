package com.shop.market.config.jwt;

import com.shop.market.config.Oauth.OAuth2Service;
import com.shop.market.dto.userD;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Configuration
@Slf4j
@RequiredArgsConstructor
@PropertySource("classpath:application-oauth.yml")
public class JwtTokenProvider {
    @Value("${app.auth.token.secret-key}")
    private String SECRET_KEY;
    private Long ACCESS_TOKEN_EXPIRE_LENGTH = 1000L * 60L * 60L;
    private Long REFRESH_TOKEN_EXPIRE_LENGTH = 1000L * 60L * 60L * 24L * 30L;
    private final OAuth2Service oAuth2Service;

    @PostConstruct
    protected void init(){
        this.SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
    }

    public String createAccessToken(userD user){
        return createToken(user, ACCESS_TOKEN_EXPIRE_LENGTH);
    }

    public String createRefreshToken(userD user){
        return createToken(user, REFRESH_TOKEN_EXPIRE_LENGTH);
    }

    public String createToken(userD user, long expireLength){
        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put("username", user.getEmail());
        Date now = new Date();
        Date validity = new Date(now.getTime() + expireLength);
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token){
        try{
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY.getBytes())
                    .build().parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        }catch(Exception e){
            return false;
        }
    }

    public Authentication getAuthentication(String token){
        return new UsernamePasswordAuthenticationToken();
    }

}
