package com.shop.market.config.jwt;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;

public interface JwtProperties {
    final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    long accessTime = 1000L * 60 * 60;
    long refreshTime = 1000L * 60 * 60 * 24 * 30;
}
