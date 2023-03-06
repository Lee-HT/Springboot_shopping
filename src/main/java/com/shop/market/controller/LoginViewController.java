package com.shop.market.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("lView")
public class LoginViewController {

    @GetMapping("login")
    public String loginMenu(){
        return null;
    }

    @GetMapping("Register")
    public String RegisterMenu(){
        return null;
    }

}
