package com.shop.market.config.Oauth;

import com.shop.market.config.jwt.JwtProperties;
import com.shop.market.config.jwt.TokenProvider;
import com.shop.market.dto.SessionUser;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Component
public class Oauth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final HttpSession httpSession;
    private final TokenProvider tokenProvider;
    private final CookieProvider cookieProvider;
    private final Long ACCESS_TOKEN_EXPIRE_LENGTH = JwtProperties.accessTime / 1000;
    private final Long REFRESH_TOKEN_EXPIRE_LENGTH = JwtProperties.refreshTime / 1000;

    @Autowired
    private Oauth2SuccessHandler(HttpSession httpSession,TokenProvider tokenProvider,CookieProvider cookieProvider){
        this.cookieProvider = cookieProvider;
        this.httpSession = httpSession;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        log.info(httpSession.getAttribute("user").toString());

        String username = sessionUser.getUsername();
        String accessToken = tokenProvider.AccessToken(username);
        String refreshToken = tokenProvider.ReFreshToken(username);

        response.setHeader("Authorization", accessToken);

        Cookie accessCookie = cookieProvider.getCookie("accessToken",accessToken,
                ACCESS_TOKEN_EXPIRE_LENGTH.intValue());
        response.addCookie(accessCookie);

        Cookie refreshCookie = cookieProvider.getCookie("refreshToken",refreshToken,
                REFRESH_TOKEN_EXPIRE_LENGTH.intValue());
        response.addCookie(refreshCookie);

        String targetUrl = UriComponentsBuilder.fromUriString("/")
//                .queryParam("", "")
                .build().toUriString();

        response.sendRedirect("targetURl");
        getRedirectStrategy().sendRedirect(request, response, targetUrl);

    }
}
