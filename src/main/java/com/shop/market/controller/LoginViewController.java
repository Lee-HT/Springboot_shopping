package com.shop.market.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("login/")
public class LoginViewController {

    @GetMapping("login")
    public String loginMenu(){
        return "login";
    }

    @GetMapping("register")
    public String RegisterMenu(){
        return "register";
    }

}
