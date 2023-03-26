package com.shop.market.mapper;

import com.shop.market.dto.userD;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface UserMapper {
    void saveUser(userD userD);

    void findUserByUsername(String username);

    void updateUserByUsername(String username, String password);

}
