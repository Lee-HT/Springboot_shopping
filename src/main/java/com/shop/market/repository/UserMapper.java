package com.shop.market.repository;

import com.shop.market.dto.userD;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    List<userD> selectUserAll();

    userD findByEmailAndProvider(Map<String,String> emAndPv);

    userD findUserByUsername(String username);

    void saveUser(userD userD);

    void deleteUser(String username);

    void updateUser(userD user);
}
