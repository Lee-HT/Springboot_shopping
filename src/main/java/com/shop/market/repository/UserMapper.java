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

    userD findByEmailAndProvider(Long id);

    userD findUserByUsername(String username);

    void saveUser(userD userD);

    void deleteUser(String username);


}
