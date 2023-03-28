package com.shop.market.repository;

import com.shop.market.dto.userD;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    void saveUser(userD userD);

    userD findUserByUsername(String username);

    void updateUserByUsername(String username, String password);

}
