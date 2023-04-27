package com.shop.market.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("jwt/")
public class jwtTokenController {
    @GetMapping("token")
    public void jwtToken(){
        log.info("jwtToken Controller");
    }
}
