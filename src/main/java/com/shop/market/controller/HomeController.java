package com.shop.market.controller;

import com.shop.market.enums.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
@RequestMapping("home/")
public class HomeController {
    @GetMapping("test")
    @ResponseBody
    public String home(){
        String testResult = Role.USER.getValue();
        log.info(String.format("test Result : %s",testResult));
        return testResult;
    }
}
