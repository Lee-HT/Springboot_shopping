package com.shop.market.controller;

import com.shop.market.config.Oauth.LoginUser;
import com.shop.market.dto.SessionUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("login")
public class LoginViewController {

    @GetMapping("/login")
    public String loginMenu(Model model,@LoginUser SessionUser user) {
        log.info("LoginView login");
        if(user != null){
            model.addAttribute("username",user);
        }

        return "user/login";
    }

    @GetMapping("/register")
    public String registerMenu() {
        return "user/register";
    }

    @GetMapping("/savePost")
    public String savePostMenu() {
        return "user/savePost";
    }

}
