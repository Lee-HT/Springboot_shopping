package com.shop.market.repository;

import com.shop.market.dto.postD;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostMapper {
    List<postD> selectPostBySeller(String seller);
    postD selectPostById(Long id);
    void insertPostBySeller(postD post);
    void deletePostById(Long id);


}
