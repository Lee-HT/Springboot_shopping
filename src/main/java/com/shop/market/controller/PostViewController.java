package com.shop.market.controller;

import com.shop.market.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("post/")
public class PostViewController {

    @Autowired
    private PostService postService;

    @GetMapping("savePost")
    public String savePost() {
        return "post/savePost";
    }

    @GetMapping("sellerPost")
    public String sellerPost(Model model, @RequestParam String seller) {
        model.addAttribute("postList",postService.sellerPost(seller));
        return "post/sellerPost";
    }
}
