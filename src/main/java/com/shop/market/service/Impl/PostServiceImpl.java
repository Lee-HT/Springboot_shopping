package com.shop.market.service.Impl;

import com.shop.market.dto.postD;
import com.shop.market.repository.PostMapper;
import com.shop.market.service.PostService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PostServiceImpl implements PostService {
    @Autowired
    private PostMapper postMapper;

    public void savePost(postD post){
        try{
            postMapper.insertPostByUsername(post);
        }catch(Exception e){
            log.info("savePost error: " + e);
        }
    };
}
