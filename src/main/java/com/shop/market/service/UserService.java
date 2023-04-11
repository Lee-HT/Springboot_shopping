package com.shop.market.service;

import com.shop.market.dto.loginD;
import com.shop.market.dto.userD;
import java.util.List;


public interface UserService {

    List<userD> findUserAll();

    userD findUser(Long id);

    void register(userD user);

    void unregister(String username);

    userD findUser(loginD login);

}
