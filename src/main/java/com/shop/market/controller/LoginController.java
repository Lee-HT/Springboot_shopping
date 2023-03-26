package com.shop.market.controller;

import com.shop.market.dto.userD;
import com.shop.market.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("loginProcess/")
public class LoginController {

    private UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("login")
    public String login() {
        log.info("Controller login");
        return "redirect:/login/login";
    }

    @PostMapping("register")
    public String Register(@Valid userD userD) {
        log.info("Controller register");
        userService.register(userD);
        return "redirect:/login/login";
    }
}
