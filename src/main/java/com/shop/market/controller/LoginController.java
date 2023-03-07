package com.shop.market.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("loginProcess/")
public class LoginController {

    @GetMapping("login")
    public String login() { return "Redirect:6040/login/login"; }

    @GetMapping("register")
    public String Register(){
        return "Redirect:6040/login/login";
    }
}
