package com.shop.market.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("login/")
public class LoginController {

    @GetMapping("Register")
    public String Register(){
        return "Redirect:6040/lView";
    }
}
