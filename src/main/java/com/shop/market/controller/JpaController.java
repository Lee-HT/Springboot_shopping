package com.shop.market.controller;

import com.shop.market.entity.userE;
import com.shop.market.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("jpa/")
@RequiredArgsConstructor
@Slf4j
public class JpaController {
    private final UserRepository userRepository;

    @GetMapping("findTest")
    @ResponseBody
    public List<userE> selectTest(){
        log.info("findTest");
        return userRepository.findAll();
    }

}
