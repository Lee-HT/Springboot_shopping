package com.shop.market.config.Oauth;

import com.shop.market.config.jwt.TokenProvider;
import com.shop.market.dto.SessionUser;
import com.shop.market.dto.userD;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class Oauth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
    private HttpSession httpSession;
    private TokenProvider tokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        log.info(httpSession.getAttribute("user").toString());

        String token = tokenProvider.CreateToken(sessionUser.getUsername());

    }
}
