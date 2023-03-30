package com.shop.market.repository;

import com.shop.market.dto.userD;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM user_table")
    List<userD> selectUserAll();
    @Insert("INSERT INTO user_table(username,password,email) VALUES(#{username}, #{password}, #{email})")
    void saveUser(userD userD);
    @Select("SELECT * FROM user_table where username = #{username}")
    userD findUserByUsername(String username);
    @Delete("DELETE * from user_table where username = #{username}")
    void deleteUser(String username);

    void updateUserByUsername(String username, String password);

}
