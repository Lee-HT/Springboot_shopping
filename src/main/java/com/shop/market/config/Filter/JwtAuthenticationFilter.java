package com.shop.market.config.Filter;

import com.shop.market.config.jwt.TokenProvider;
import com.shop.market.dto.SessionUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String token = tokenProvider.resolveToken(request);
        log.info("token : " + token);

        if(token != null && tokenProvider.validationToken(token)){
            try{
                Authentication authentication = tokenProvider.getAuthentication(token);
                log.info(authentication.toString());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }catch(Exception e){
                log.error("사용자 없음 " + e);
            }

        }

        log.info("jwt filter");
        filterChain.doFilter(request,response);
    }
}
