package com.shop.market.config.Filter;

import com.shop.market.config.Cookie.CookieProvider;
import com.shop.market.config.jwt.JwtProperties;
import com.shop.market.config.jwt.TokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final CookieProvider cookieProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        HashMap<String, String> tokens = tokenProvider.resolveToken(request);

        try {
            if (tokens != null) {
                String accessToken = tokens.get("accessToken");
                String refreshToken = tokens.get("refreshToken");

                if (tokenProvider.validationToken(accessToken)) {
                    Authentication authentication = tokenProvider.getAuthentication(
                            accessToken);
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    log.info("권한 인증");
                } else if (tokenProvider.validationToken(refreshToken)) {
                    String username = tokenProvider.getUsername(refreshToken);

                    // ms -> sec
                    Long ACCESS_TIME = JwtProperties.accessTime / 1000;
                    Cookie cookie = cookieProvider.getCookie("accessToken", accessToken,
                            ACCESS_TIME.intValue());
                    response.addCookie(cookie);
                    log.info("Token refresh");
                }

                log.info("accessToken : " + accessToken + " refreshToken : " + refreshToken);
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("error : " + e);
        }
    }

}

