package com.shop.market.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("login/")
public class LoginViewController {

    @GetMapping("login")
    public String loginMenu(Model model) {
        model.addAttribute("username", "user1");
        return "login";
    }

    @GetMapping("register")
    public String registerMenu() {
        return "register";
    }

    @GetMapping("savePost")
    public String savePostMenu() {
        return "savePost";
    }

}
