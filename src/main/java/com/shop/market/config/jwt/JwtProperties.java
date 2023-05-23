package com.shop.market.config.jwt;

public interface JwtProperties {
    String secretKey = "secretKey";
//    long accessTime = 1000L * 60 * 60;
    long accessTime = 1000L * 30;
    long refreshTime = 1000L * 60 * 60 * 24 * 30;
}
