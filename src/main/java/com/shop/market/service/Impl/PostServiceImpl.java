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

    @Override
    public void savePost(postD post){
        try{
            postMapper.insertPostBySeller(post);
        }catch(Exception e){
            log.info("savePost error: " + e);
        }
    };

    @Override
    public List<postD> sellerPost(String seller){
        try{
            log.info("sellerPostService");
            return postMapper.selectPostBySeller(seller);
        }catch(Exception e){
            log.info("sellerPost error " + e);
            return null;
        }
    }

    @Override
    public postD deletePost(Long id){
        try{
            log.info("deletePostService");
            postD deleted = postMapper.selectPostById(id);
            postMapper.deletePostById(id);
            return deleted;
        }catch(Exception e){
            log.info("deletePost error");
            return null;
        }
    }
}
