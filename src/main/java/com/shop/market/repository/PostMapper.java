package com.shop.market.repository;

import com.shop.market.dto.postD;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostMapper {
    void insertPostBySeller(postD post);

    List<postD> selectPostBySeller(String seller);

}
