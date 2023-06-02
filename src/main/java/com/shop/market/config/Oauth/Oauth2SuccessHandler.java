package com.shop.market.config.Oauth;

import com.shop.market.config.Cookie.CookieProvider;
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

@Slf4j
@Component
public class Oauth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final HttpSession httpSession;
    private final TokenProvider tokenProvider;
    private final CookieProvider cookieProvider;
    private final Long REFRESH_TOKEN_EXPIRE = JwtProperties.refreshTime / 1000;

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
        String accessToken = tokenProvider.getAccessToken(username);
        String refreshToken = tokenProvider.getReFreshToken(username);

        response.setHeader("Authorization", accessToken);

        Cookie accessCookie = cookieProvider.getCookie("accessToken",accessToken,
                -1);
        response.addCookie(accessCookie);

        Cookie refreshCookie = cookieProvider.getCookie("refreshToken",refreshToken,
                REFRESH_TOKEN_EXPIRE.intValue());
        response.addCookie(refreshCookie);

        log.info("Oauth2Success");

        super.onAuthenticationSuccess(request,response,authentication);

//        String targetUrl = UriComponentsBuilder.fromUriString("/")
//                .queryParam("", "")
//                .build().toUriString();
//
//        response.sendRedirect(targetUrl);
//        getRedirectStrategy().sendRedirect(request, response, targetUrl);

    }
}
