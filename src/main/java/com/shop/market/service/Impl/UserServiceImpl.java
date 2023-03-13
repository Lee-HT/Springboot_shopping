package com.shop.market.service.Impl;

import com.shop.market.dto.userD;
import com.shop.market.mapper.UserMapper;
import com.shop.market.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private PasswordEncoder passwordEncoder;

    private UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, PasswordEncoder passwordEncoder){
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(userD userD){
        userMapper.saveUser(userD);

    }
}
