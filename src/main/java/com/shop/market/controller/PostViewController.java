package com.shop.market.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("post/")
public class PostViewController {
    @GetMapping("savePost")
    public String savePost(){
        return "savePost";
    }
}
