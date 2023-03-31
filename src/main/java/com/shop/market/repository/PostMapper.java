package com.shop.market.repository;

import com.shop.market.dto.postD;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostMapper {
    void insertPostByUsername(postD post);


}
