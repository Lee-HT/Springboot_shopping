package com.shop.market.controller;

import com.shop.market.dto.UserD;
import com.shop.market.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("loginprocess/")
public class LoginController {
    private UserService userService;

    @Autowired
    public LoginController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("login")
    public String login() { return "Redirect:6040/login/login"; }

    @PostMapping("register")
    public String Register(@Valid UserD userD){
        log.info("실행 테스트");
        userService.register(userD);
        return "Redirect:6040/login/login";
    }
}
