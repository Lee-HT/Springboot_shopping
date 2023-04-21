//package com.shop.market.config.jwt;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import jakarta.annotation.PostConstruct;
//import java.util.Base64;
//import java.util.Date;
//import org.springframework.stereotype.Service;
//
//@Service
//public class TokenService {
//
//    private String secretKey = "token-secret-key";
//
//    @PostConstruct
//    protected void init() {
//        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
//    }
//
//    @Override
//    public Token generateToken(String uid, String role) {
//        long tokenPeriod = 1000L * 60L * 60L;
//        long refreshPeriod = 1000L * 60L * 60L * 24L * 30L;
//
//        Claims claims = Jwts.claims().setSubject(uid);
//        claims.put("role", role);
//
//        Date now = new Date();
//        return new Token(
//                Jwts.builder()
//                        .setClaims(claims)
//                        .setIssuedAt(now)
//                        .setExpiration(new Date(now.getTime() + tokenPeriod))
//                        .signWith(SignatureAlgorithm.HS256,secretKey)
//                        .compact(),
//                Jwts.builder()
//                        .setClaims(claims)
//                        .setIssuedAt(now)
//                        .setExpiration(new Date(now.getTime() + refreshPeriod))
//                        .signWith(SignatureAlgorithm.HS256,secretKey)
//                        .compact()
//        );
//    }
//
//
//
//}
