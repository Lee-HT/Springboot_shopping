package com.shop.market.repository;

import com.shop.market.dto.userD;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    List<userD> selectUserAll();

    userD findUserByUsername(String username);

    void saveUser(userD userD);

//    @Delete("DELETE * from user_table where username = #{username}")
    void deleteUser(String username);

    void updateUserByUsername(String username, String password);

}