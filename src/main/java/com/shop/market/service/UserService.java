package com.shop.market.service;

import com.shop.market.dto.loginD;
import com.shop.market.dto.userD;


public interface UserService {

//    void login();
    void register(userD user);

    userD findUser(loginD login);

}
