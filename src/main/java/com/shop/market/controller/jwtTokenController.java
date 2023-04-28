package com.shop.market.controller;

import com.shop.market.config.Oauth.LoginUser;
import com.shop.market.config.jwt.TokenProvider;
import com.shop.market.dto.userD;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequestMapping("jwt/")
@RequiredArgsConstructor
public class jwtTokenController {
    private final TokenProvider tokenProvider;
    @GetMapping("token")
    @ResponseBody
    public ResponseEntity<String> jwtToken(@LoginUser userD user){
        log.info("jwtToken Controller");
        userD userB = userD.builder()
                .username(user.getUsername())
                .email(user.getEmail()).role(user.getRole()).picture(user.getPicture())
                .build();
        String token = tokenProvider.CreateToken(user.getUsername());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",token);
        return new ResponseEntity<>(token,headers,HttpStatus.OK);
    }
}
