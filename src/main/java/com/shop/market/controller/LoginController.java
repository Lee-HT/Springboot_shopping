package com.shop.market.controller;

import com.shop.market.dto.UserD;
import com.shop.market.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("loginProcess/")
public class LoginController {


    @PostMapping("login")
    public String login() { return "Redirect:6040/login/login"; }

    @PostMapping("register")
    public String Register(@Valid UserD userD){
        return "Redirect:6040/login/login";
    }
}
