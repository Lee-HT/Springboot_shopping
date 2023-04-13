package com.shop.market.service;

import com.shop.market.dto.postD;
import java.util.List;

public interface PostService {

    void savePost(postD post);

    List<postD> postAll();

    postD selectPostById(Long id);

    postD deletePost(Long id);

    List<postD> sellerPost(String seller);
}
