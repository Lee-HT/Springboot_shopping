package com.shop.market.controller;

import com.shop.market.dto.loginD;
import com.shop.market.dto.userD;
import com.shop.market.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("findUser")
    public String findUser(@RequestBody loginD login){
        log.info("Controller findUser");
        userService.findUser(login);
        return "redirect:/login/login";
    }

    @PostMapping("login")
    public String login(@RequestBody loginD login) {
        log.info("Controller login");
        return "redirect:/login/login";
    }

    @PostMapping("register")
    public String Register(@RequestBody @Valid userD user) {
        log.info("Controller register");
        userService.register(user);
        return "redirect:/login/login";
    }
}
