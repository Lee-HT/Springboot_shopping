package com.shop.market.controller;

import com.shop.market.dto.postD;
import com.shop.market.service.PostService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequestMapping("postProcess/")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("savePost")
    public @ResponseBody postD savePost(@RequestParam HashMap<String, String> postMap) {
        log.info("postController savePost");
        log.info(postMap.get("seller"));
        log.info(postMap.get("title"));
        log.info(postMap.get("content"));
        postD post = postD.builder().seller(postMap.get("seller"))
                .title(postMap.get("title"))
                .content(postMap.get("content")).build();
        postService.savePost(post);
        return post;
    }

    @PostMapping("sellerPost")
    public @ResponseBody List<postD> sellerPost(@RequestParam String seller){
        log.info("postController selectPost");
        List<postD> postList = postService.sellerPost(seller);
        log.info(postList.get(0).getSeller());
        log.info(postList.get(0).getTitle());
        log.info(postList.get(0).getContent());
        return postList;
    }

}
