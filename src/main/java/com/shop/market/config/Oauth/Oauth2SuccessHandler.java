package com.shop.market.config.Oauth;

import com.shop.market.config.jwt.JwtProperties;
import com.shop.market.config.jwt.TokenProvider;
import com.shop.market.dto.SessionUser;
import com.shop.market.dto.userD;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
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

    private final Long ACCESS_TOKEN_EXPIRE_LENGTH = JwtProperties.accessTime / 1000;
    private final Long REFRESH_TOKEN_EXPIRE_LENGTH = JwtProperties.refreshTime / 1000;

    @Autowired
    private Oauth2SuccessHandler(HttpSession httpSession,TokenProvider tokenProvider){
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
        String refreshToken = tokenProvider.ReFreshToken();

        response.setHeader("Authorization", accessToken);

        Cookie accessCookie = tokenCookie("accessToken",accessToken,
                ACCESS_TOKEN_EXPIRE_LENGTH.intValue());
        response.addCookie(accessCookie);

        Cookie refreshCookie = tokenCookie("refreshToken","refresh",
                REFRESH_TOKEN_EXPIRE_LENGTH.intValue());
        response.addCookie(refreshCookie);

        log.info(accessToken);

        String targetURl = UriComponentsBuilder.fromUriString("/")
//                .queryParam("", "")
                .build().toUriString();

        getRedirectStrategy().sendRedirect(request, response, targetURl);

    }

    //token 담을 쿠키
    private Cookie tokenCookie(String name, String value,int expire){
        Cookie cookie = new Cookie(name,value);
        cookie.setMaxAge(expire);
        cookie.setHttpOnly(true);
        cookie.setDomain("localhost");
        cookie.setPath("/");

//        cookie.setSecure(true);
        return cookie;
    }
}
