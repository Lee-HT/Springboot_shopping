package com.shop.market.controller;

import com.shop.market.dto.postD;
import com.shop.market.service.PostService;
import java.util.HashMap;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequestMapping("post")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("")
    public @ResponseBody postD savePost(postD post) {
        log.info("postController savePost");
        log.info(post.getSeller());
        log.info(post.getTitle());
        log.info(post.getContent());
        try{
            postService.savePost(post);
            return post;
        }catch (Exception e){
            log.info("savePost error");
            return null;
        }
    }

    @GetMapping("")
    public @ResponseBody List<postD> sellerPost(@RequestParam String seller){
        log.info("postController selectPost");
        List<postD> postList = postService.sellerPost(seller);
        log.info(postList.get(0).getSeller());
        log.info(postList.get(0).getTitle());
        log.info(postList.get(0).getContent());
        return postList;
    }

    @DeleteMapping("")
    public @ResponseBody postD deletePost(@RequestBody HashMap<String,Long> id){
        log.info("postController deletePost");
        return postService.deletePost(id.get("id"));
    }

}
