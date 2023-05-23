package com.shop.market.config.Oauth;

import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Component;

@Component
public class CookieProvider {

    // Token 담을 쿠키
    public Cookie getCookie(String name, String value,int expire){
        Cookie cookie = new Cookie(name,value);
        cookie.setMaxAge(expire);
        cookie.setHttpOnly(true);
        cookie.setDomain("localhost");
        cookie.setPath("/");

        return cookie;
    }


}
