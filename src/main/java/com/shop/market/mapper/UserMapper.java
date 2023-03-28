package com.shop.market.mapper;

import com.shop.market.dto.userD;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {
    void saveUser(userD userD);

    userD findUserByUsername(String username);

    void updateUserByUsername(String username, String password);

}
