package com.shop.market.service;

import com.shop.market.dto.loginD;
import com.shop.market.dto.userD;
import java.util.List;
import java.util.Map;


public interface UserService {

    userD findByEmail(String email);

//    List<userD> findUserAll();
//    void register(userD user);
//    void unregister(String username);
//    userD findUser(loginD login);
//    void updateUser(userD user);

}
