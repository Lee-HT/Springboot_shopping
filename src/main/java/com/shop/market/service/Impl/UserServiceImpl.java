package com.shop.market.service.Impl;

import com.shop.market.dto.loginD;
import com.shop.market.dto.userD;
import com.shop.market.mapper.UserMapper;
import com.shop.market.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private PasswordEncoder passwordEncoder;

    private UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, PasswordEncoder passwordEncoder){
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(userD user){
        log.info("UserService register");
        userMapper.saveUser(user);
    }

    public userD findUser(loginD login){
        log.info("UserService findUser");
        userD user = userMapper.findUserByUsername(login.getUsername());

        return user;
    }
}
