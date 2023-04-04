package com.shop.market.controller;

import com.shop.market.dto.postD;
import com.shop.market.service.PostService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
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
        log.info("PostViewController sellerPost");
        try{
            List<postD> posts = postService.sellerPost(seller);
            log.info("seller : "+ seller);
            log.info(posts.get(0).getSeller());
            log.info(posts.get(0).getTitle());
            log.info(posts.get(0).getContent());
        }catch(Exception e){
            log.info("PostViewController error");
        }

        model.addAttribute("postList",postService.sellerPost(seller));
        return "post/sellerPost";
    }
}
