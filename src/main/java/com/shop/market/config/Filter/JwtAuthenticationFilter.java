package com.shop.market.config.Filter;

import com.shop.market.config.Oauth.CookieProvider;
import com.shop.market.config.jwt.JwtProperties;
import com.shop.market.config.jwt.TokenProvider;
import io.jsonwebtoken.Jwt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;
    private final CookieProvider cookieProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        HashMap<String, String> tokens = tokenProvider.resolveToken(request);
        String accessToken = tokens.get("accessToken");
        String refreshToken = tokens.get("refreshToken");

        if (tokenProvider.validationToken(accessToken)) {
            try {
                Authentication authentication = tokenProvider.getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("권한 인증");
            } catch (Exception e) {
                log.error("사용자 없음 " + e);
            }
        } else if (tokenProvider.validationToken(refreshToken)) {
            try {
                String username = tokenProvider.getUsername(refreshToken);

                Long ACCESS_TIME =  JwtProperties.accessTime / 1000;
                Cookie cookie = cookieProvider.getCookie("accessToken",accessToken, ACCESS_TIME.intValue());

                response.addCookie(cookie);
                log.info("new accessToken");
            }catch (Exception e){
                log.error("refreshToken error : " + e);
            }
        }

        log.info("accessToken : " + accessToken);
        log.info("refreshToken : " + refreshToken);
        filterChain.doFilter(request, response);
    }
}
