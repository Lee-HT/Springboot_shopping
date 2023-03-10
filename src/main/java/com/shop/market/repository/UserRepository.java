package com.shop.market.repository;

import com.shop.market.dto.UserD;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRepository {
    void saveUser(UserD userD);

    void findUserByUsername(String username);

    void updateUserByUsername(String username, String password);

}
