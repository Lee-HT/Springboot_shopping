package com.shop.market.config.Oauth;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Component;

@Component
public class CookieProvider {
    private Cookie cookie;

    public void setCookie(String name){
        this.cookie = new Cookie(name,null);
        this.cookie.setHttpOnly(true);
        this.cookie.setDomain("localhost");
        this.cookie.setPath("/");
    }

    // Token 담을 쿠키
    public Cookie getCookie(String name, String value,int expire){
        setCookie(name);
        this.cookie.setValue(value);
        this.cookie.setMaxAge(expire);

        return this.cookie;
    }

    public Cookie expireCookie(String name){
        setCookie(name);
        this.cookie.setMaxAge(0);

        return this.cookie;
    }


}
